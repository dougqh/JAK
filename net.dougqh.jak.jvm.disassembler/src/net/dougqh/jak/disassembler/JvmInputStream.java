package net.dougqh.jak.disassembler;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.LinkedList;

final class JvmInputStream {
	private static final String EOF_MESSAGE = "Unexpected end of class";

	private final LinkedList<byte[]> bytesQueue = new LinkedList<byte[]>();
	private int pos;
	
	JvmInputStream( final byte[] bytes ) throws IOException {
		this.bytesQueue.add( new Bytes(bytes) );
	}
	
	JvmInputStream( final InputStream in ) throws IOException {
		super( getBytes(in) );
	}
	
	private static final LinkedList<byte[]> getBytes( final InputStream in )
		throws IOException
	{
		LinkedList<byte[]> list = new LinkedList<byte[]>();
		
		byte[] buffer;
		int numRead;
		while ( ! Thread.currentThread().isInterrupted() ) {
			buffer = new byte[ bufferSize(in) ];
			numRead = in.read(buffer);
			
			list.add(buffer);
		}
		
		return list;
	}
	
	private final int bufferSize( final InputStream in ) throws IOException {
		return Math.max(512, in.available());
	}
	
	JvmInputStream( final byte[] bytes ) {
		this.bytes = bytes;
		this.pos = 0;
	}
	
	final byte u1() throws IOException {
		if ( this.pos >= this.bytes.length ) {
			throw new EOFException(EOF_MESSAGE);
		}
		return this.bytes[ this.pos++ ];
	}
	
	final byte[] read( final int length ) throws IOException {
		byte[] bytes = new byte[ length ];
		
		int numRead = this.in.read( bytes );
		if ( numRead != bytes.length ) {
			throw new EOFException(EOF_MESSAGE);
		}
		
		return bytes;
	}
	
	private final ByteBuffer readByteBuffer( final int length ) throws IOException {
		return ByteBuffer.wrap( this.read( length ) );
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
		byte[] bytes = this.read(byteLength);
		return new String(bytes, "utf8");
	}
	
	final void assertDone() throws IOException {
		if ( this.in.read() == -1 ) {
			throw new ClassFileFormatException("Expected EOF");
		}
	}
	
	private final class Bytes {
		private final byte[] bytes;
		private final int length;
		private int pos;
		
		public Bytes( final byte[] bytes ) {
			super( bytes, bytes.length );
		}
		
		public Bytes( final byte[] bytes, final int length ) {
			this.bytes = bytes;
			this.length = length;
		}
	}
}
