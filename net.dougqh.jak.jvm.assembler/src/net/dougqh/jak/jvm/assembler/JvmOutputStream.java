package net.dougqh.jak.jvm.assembler;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;

final class JvmOutputStream {
	private byte[] data;
	private int pos;
	
	JvmOutputStream( final int size ) {
		this.data = new byte[ size ];
		this.pos = 0;
	}
	
	final JvmOutputStream u1( final byte byte1 ) {
		return this.write( byte1 );
	}
	
	final JvmOutputStream u1( final ConstantEntry entry ) {
		return this.u1( entry.index() );
	}
	
	final JvmOutputStream u1( final int byte1 ) {
		return this.write( (byte)byte1 );
	}
	
	final JvmOutputStream u2( final ConstantEntry entry ) {
		return this.u2( entry.index() );
	}
	
	final JvmOutputStream u2( final int num ) {
		this.shortBuffer().put( (short)num );
		return this;
	}
	
	final JvmOutputStream u4( final int num ) {
		this.intBuffer().put( num );
		return this;
	}

	final JvmOutputStream u4( final float num ) {
		return this.u4( Float.floatToRawIntBits( num ) );
	}
	
	final JvmOutputStream u8( final long num ) {
		this.longBuffer().put( num );
		return this;
	}

	final JvmOutputStream u8( final double num ) {
		return this.u8( Double.doubleToRawLongBits( num ) );
	}
		
	final JvmOutputStream utf8( final String value ) {
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
	
	private final JvmOutputStream write( final byte aByte ) {
		this.ensureCapacity( this.pos + 1 );
		this.data[ this.pos++ ] = aByte;
		return this;
	}
	
	private final JvmOutputStream write( final byte[] bytes ) {
		this.ensureCapacity( this.pos + bytes.length );
		System.arraycopy(
			bytes, 0, 
			this.data, this.pos,
			bytes.length );
		this.pos += bytes.length;
		return this;
	}

	final int length() {
		return this.pos;
	}
	
	final void writeTo( final JvmOutputStream destination ) {
		destination.ensureCapacity( destination.pos + this.length() );
		System.arraycopy(
			this.data, 0,
			destination.data, destination.pos, this.length() );
		destination.pos += this.length();
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
	
	private final void ensureCapacity( final int length ) {
		if ( length > this.data.length ) {
			int newLength;
			for ( newLength = this.data.length; newLength < length; newLength <<= 1 ) {
			}
			
			this.data = Arrays.copyOf( this.data, newLength );
		}
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
				ByteBuffer.wrap( JvmOutputStream.this.data, this.pos, 2 ).asShortBuffer();
			buffer.put( (short)value );
		}
	}
}
