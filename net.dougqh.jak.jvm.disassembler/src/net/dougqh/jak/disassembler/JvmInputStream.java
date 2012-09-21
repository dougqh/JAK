package net.dougqh.jak.disassembler;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

final class JvmInputStream {
	// A possibly overly fanciful construct for working with a byte stream 
	// efficiently while providing a convenient definitive stop indicator.
	// Chunks of the available size of the InputStream are fed into a queue.
	// When a sub-stream is needed the byte[]s can be shared to lower the 
	// copy time and memory footprint.
	private static final String EOF_MESSAGE = "Unexpected end of class";

	private final LinkedList<byte[]> bytesQueue;
	private int headPos;
	
	private List<? extends byte[]> resetQueue;
	
	JvmInputStream(final byte[] bytes) {
		this(toQueue(bytes));
	}
	
	private static final LinkedList<byte[]> toQueue(final byte[] bytes) {
		LinkedList<byte[]> queue = new LinkedList<byte[]>();
		queue.add(bytes);
		return queue;
	}
	
	//Convenience 
	JvmInputStream(final byte[]... bytes) {
		this(toQueue(bytes));
	}
	
	private static final LinkedList<byte[]> toQueue(final byte[]... bytes) {
		LinkedList<byte[]> queue = new LinkedList<byte[]>();
		queue.addAll(Arrays.asList(bytes));
		return queue;
	}
	
	JvmInputStream(final InputStream in) throws IOException {
		this(loadBytes(in));
	}
	
	final JvmInputStream enableReset() {
		if ( this.headPos != 0 ) {
			// This check is imperfect because we could have moved to the next block
			// and reset the head position which is still a violation of the contract.
			// None-the-less, this sanity will hopefully catch most violators of the 
			// contract eventually.
			throw new IllegalStateException("Reset must be enabled immediately after construction.");
		}
		this.resetQueue = Collections.unmodifiableList(new LinkedList<byte[]>(this.bytesQueue));
		
		return this;
	}
	
	final JvmInputStream reset() {
		if ( this.resetQueue == null ) {
			throw new IllegalStateException("Reset was not enabled.");
		}
		this.bytesQueue.clear();
		this.bytesQueue.addAll(this.resetQueue);
		this.headPos = 0;
		
		return this;
	}
	
	private static final LinkedList<byte[]> loadBytes( final InputStream in )
		throws IOException
	{
		LinkedList<byte[]> bytesQueue = new LinkedList<byte[]>();
		
		while ( ! Thread.currentThread().isInterrupted() ) {
			int bufferSize = bufferSize(in);
			byte[] buffer = new byte[bufferSize];
			int numRead = in.read(buffer);
			
			if ( numRead < 0 ) {
				break;
			}
			
			if ( numRead < bufferSize ) {
				bytesQueue.add(Arrays.copyOf(buffer, numRead));
			} else {
				bytesQueue.add(buffer);
			}
		}
		
		return bytesQueue;
	}
	
	private JvmInputStream(final LinkedList<byte[]> bytesQueue) {
		this.bytesQueue = bytesQueue;
		this.headPos = 0;
	}
	
	private static final int bufferSize(final InputStream in) throws IOException {
		return Math.max(512, in.available());
	}
	
	private final byte[] head() throws EOFException {
		if ( this.bytesQueue.isEmpty() ) {
			throw new EOFException(EOF_MESSAGE);
		}
		return this.bytesQueue.getFirst();
	}

	private final byte[] readHeadChunk(final int length)
		throws EOFException
	{
		byte[] head = this.head();
		int headRemaining = head.length - this.headPos;
		
		// special case use to avoid a copy when doing 
		// sub-streams of large blocks
		if ( this.headPos == 0 && length > head.length ) {
			this.removeHead();
			return head;
		} 
		
		if ( length < headRemaining ) {
			byte[] bytes = new byte[length];
			System.arraycopy(
				head, this.headPos,
				bytes, 0,
				length);
			
			this.headPos += length;
			return bytes;
		} else {
			byte[] bytes = new byte[headRemaining];
			System.arraycopy(
				head, this.headPos,
				bytes, 0,
				headRemaining);
			
			this.removeHead();
			return bytes;
		}
	}
	
	private final int readHead(final byte[] bytes, final int pos)
		throws EOFException
	{
		return this.readHead(bytes, pos, bytes.length - pos);
	}
	
	private final int readHead(
		final byte[] bytes,
		final int pos,
		final int needed)
		throws EOFException
	{
		byte[] head = this.head();
		int headRemaining = head.length - this.headPos;
		
		if ( headRemaining > needed ) {
			System.arraycopy(
				head, this.headPos,
				bytes, pos,
				needed );
			
			this.headPos += needed;
			return needed;
		} else {
			System.arraycopy(
				head, this.headPos,
				bytes, pos,
				headRemaining );
			
			// No need to increment head position, we already know
			// we need to move to the next block.
			// So just remove the current head which resets the head
			// position to zero anyway.
			this.removeHead();
			return headRemaining;
		}

	}
	
	private final void removeHead() {
		this.bytesQueue.removeFirst();
		this.headPos = 0;
	}
	
	final byte u1() throws EOFException {
		// No need for a loop or bounds check
		// The class invariants guarantee that no block is empty 
		// and that a fully consumed block has been removed from 
		// the head by the previous  method call.
		// So, the only check is if the queue is completely empty
		// which head performs automatically.
		byte[] head = this.head();
		
		byte value = head[this.headPos++];
		
		if ( this.headPos == head.length ) {
			this.removeHead();
		}
		return value;
	}
	
	final byte[] readBytes(final int length) throws EOFException {
		byte[] bytes = new byte[length];
		int pos = 0;
		
		while ( pos < bytes.length ) {
			pos += this.readHead(bytes, pos);
		}
		return bytes;
	}
	
	final ByteBuffer readByteBuffer(final int length) throws EOFException {
		byte[] head = this.head();
		if ( this.headPos + length < head.length ) {
			// if solely contained in the current block, just construct the 
			// buffer directly around the sub-section of the array
			ByteBuffer buffer = ByteBuffer.wrap(head, this.headPos, length);
			this.headPos += length;
			return buffer;
		} else {
			// Otherwise, assembled an array with necessary bytes and use it
			// Given the minimum block size vs the usual buffer size, this 
			// case should be infrequent.
			return ByteBuffer.wrap(this.readBytes(length));			
		}
	}
	
	final JvmInputStream readSubStream(final int length) throws EOFException {
		LinkedList<byte[]> subQueue = new LinkedList<byte[]>();
		
		// First block could be not at a zero pos, so handle it as a special case
		// All subsequent blocks that are copied will be fresh and at a headPos of zero
		int remaining = length;
		
		while ( remaining > 0 ) {
			byte[] chunk = this.readHeadChunk(remaining);
			subQueue.add(chunk);
			remaining -= chunk.length;
		}
		
		return new JvmInputStream(subQueue);
	}
	
	final short u2() throws IOException {
		return this.readByteBuffer(2).asShortBuffer().get();
	}
	
	final int u4() throws IOException {
		return this.readByteBuffer(4).asIntBuffer().get();
	}
	
	final float u4Float() throws IOException {
		return this.readByteBuffer(4).asFloatBuffer().get();		
	}
	
	final long u8() throws IOException {
		return this.readByteBuffer(8).asLongBuffer().get();
	}

	final double u8Double() throws IOException {
		return this.readByteBuffer(8).asDoubleBuffer().get();
	}
	
	final String utf8( final int byteLength ) throws IOException {
		byte[] bytes = this.readBytes(byteLength);
		return new String(bytes, "utf8");
	}
	
	final boolean isEof() {
		return this.bytesQueue.isEmpty();
	}
}
