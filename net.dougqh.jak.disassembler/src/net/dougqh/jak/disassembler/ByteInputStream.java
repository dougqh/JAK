package net.dougqh.jak.disassembler;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

final class ByteInputStream implements Closeable {
	private static final String EOF_MESSAGE = "Unexpected end of class";

	private final InputStream in;
	
	ByteInputStream( final byte[] data ) {
		this( new ByteArrayInputStream( data ) );
	}
	
	ByteInputStream( final InputStream in ) {
		this.in = in;
	}
	
	final byte u1() throws IOException {
		int value = this.in.read();
		if ( value == -1 ) {
			throw new EOFException( EOF_MESSAGE );
		}
		return (byte)value;
	}
	
	final byte[] read( final int length ) throws IOException {
		byte[] bytes = new byte[ length ];
		
		int numRead = this.in.read( bytes );
		if ( numRead != bytes.length ) {
			throw new EOFException( EOF_MESSAGE );
		}
		
		return bytes;
	}
	
	private final ByteBuffer readByteBuffer( final int length ) throws IOException {
		return ByteBuffer.wrap( this.read( length ) );
	}
	
	final short u2() throws IOException {
		return this.readByteBuffer( 2 ).asShortBuffer().get();
	}
	
	final int u4() throws IOException {
		return this.readByteBuffer( 4 ).asIntBuffer().get();
	}
	
	final float u4Float() throws IOException {
		return this.readByteBuffer( 4 ).asFloatBuffer().get();		
	}
	
	final long u8() throws IOException {
		return this.readByteBuffer( 8 ).asLongBuffer().get();
	}

	final double u8Double() throws IOException {
		return this.readByteBuffer( 8 ).asDoubleBuffer().get();
	}
	
	final String utf8( final int byteLength ) throws IOException {
		byte[] bytes = this.read( byteLength );
		return new String( bytes, "utf8" );
	}
	
	final void assertDone() throws IOException {
		if ( this.in.read() == -1 ) {
			throw new ClassFileFormatException( "Expected EOF" );
		}
	}
	
	@Override
	public final void close() throws IOException {
		this.in.close();
	}
}
