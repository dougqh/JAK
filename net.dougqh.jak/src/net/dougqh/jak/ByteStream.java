package net.dougqh.jak;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

final class ByteStream {
	private byte[] data;
	private int pos;
	
	ByteStream( final int size ) {
		this.data = new byte[ size ];
		this.pos = 0;
	}
	
	final ByteStream u1( final byte byte1 ) {
		return this.write( byte1 );
	}
	
	final ByteStream u1( final int byte1 ) {
		return this.write( (byte)byte1 );
	}
	
	final ByteStream u2( final ConstantEntry entry ) {
		return this.u2( entry.index() );
	}
	
	final ByteStream u2( final int num ) {
		this.shortBuffer().put( (short)num );
		return this;
	}
	
	final ByteStream u4( final int num ) {
		this.intBuffer().put( num );
		return this;
	}

	
	final ByteStream u4( final float num ) {
		return this.u4( Float.floatToRawIntBits( num ) );
	}
	
	final ByteStream u8( final long num ) {
		this.longBuffer().put( num );
		return this;
	}

	final ByteStream u8( final double num ) {
		return this.u8( Double.doubleToRawLongBits( num ) );
	}
		
	final ByteStream utf8( final String value ) {
		//TODO: DQH - 12-28-2009 - Implement properly
		//Java's format is not quite the same as normal UTF-8.
		try {
			byte[] bytes = value.getBytes( "utf8" );
			this.u2( bytes.length );
			this.write( bytes );
			return this;
		} catch ( UnsupportedEncodingException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	final Byte2Slot reserve2Slot() {
		//DQH - First save the position
		//Second - write place-holder bytes
		//Third - return a slot that overwrites the position
		int pos = this.pos;
		this.u2( 0 );
		return new Byte2SlotImpl( pos );
	}
	
	private final ByteBuffer byteBuffer( final int size ) {
		int oldPos = this.pos;
		this.ensureCapacity( oldPos + size );
		this.pos = oldPos + size;
		return ByteBuffer.wrap( this.data, oldPos, size );
	}
	
	private final ShortBuffer shortBuffer() {
		return this.byteBuffer( 2 ).asShortBuffer();
	}
	
	private final IntBuffer intBuffer() {
		return this.byteBuffer( 4 ).asIntBuffer();
	}
	
	private final LongBuffer longBuffer() {
		return this.byteBuffer( 8 ).asLongBuffer();
	}
	
	private final ByteStream write( final byte aByte ) {
		this.ensureCapacity( this.pos + 1 );
		this.data[ this.pos++ ] = aByte;
		return this;
	}
	
	private final ByteStream write( final byte[] bytes ) {
		this.ensureCapacity( this.pos + bytes.length );
		System.arraycopy(
			bytes, 0, 
			this.data, this.pos,
			bytes.length );
		this.pos += bytes.length;
		return this;
	}
	
	private final void ensureCapacity( final int length ) {
		if ( length > this.data.length ) {
			byte[] data = new byte[ this.data.length << 1 ];
			System.arraycopy( this.data, 0, data, 0, this.pos );
			this.data = data;
		}
	}

	final int length() {
		return this.pos;
	}
	
	final void writeTo( final ByteStream destination ) {
		//TODO: Make this more efficient
//		destination.ensureCapacity( destination.pos + this.length() );
//		System.arraycopy(
//			this.data, 0,
//			destination.data, destination.pos, this.length() );
		destination.write( this.toByteArray() );
	}
	
	final byte[] toByteArray() {
		byte[] data = new byte[ this.pos ];
		System.arraycopy( this.data, 0, data, 0, this.pos );
		return data;
	}
	
	final Class< ? > defineType(
		final TypeWriterGroup.DynamicClassLoader classLoader,
		final String name )
	{
		return classLoader.defineType(
			name,
			this.data, 0, this.pos );
	}
	
	private final class Byte2SlotImpl extends Byte2Slot {
		private final int pos;
		
		Byte2SlotImpl( final int pos ) {
			this.pos = pos;
		}
		
		@Override
		final int offset() {
			return this.pos;
		}
		
		@Override
		final void u2( final int value ) {
			ShortBuffer buffer = 
				ByteBuffer.wrap( ByteStream.this.data, this.pos, 2 ).asShortBuffer();
			buffer.put( (short)value );
		}
	}
}
