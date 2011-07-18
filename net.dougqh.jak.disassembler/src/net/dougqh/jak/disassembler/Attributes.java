package net.dougqh.jak.disassembler;

import static net.dougqh.jak.core.Attributes.*;

import java.io.IOException;

final class Attributes {
	private final Attribute[] attributes;
	
	Attributes( final ConstantPool constantPool, final ByteInputStream in )
		throws IOException
	{
		int count = in.u2();
		this.attributes = new Attribute[ count ];
		
		for ( int i = 0; i < count; ++i ) {
			int nameIndex = in.u2();
			int length = in.u4();
			byte[] data = in.read( length );
			
			this.attributes[ i ] = new Attribute(
				constantPool,
				nameIndex,
				data );
		}
	}
	
	final CodeAttribute getCode() {
		return this.get( 
			CODE,
			new Converter< CodeAttribute >() {
				CodeAttribute convert( final ByteInputStream in ) throws IOException {
					return new CodeAttribute( in );
				}
			} );
	}
	
	private final < T > T get(
		final String name,
		final Converter< T > converter )
	{
		Attribute attribute = this.find( CODE );
		if ( attribute == null ) {
			return null;
		} else {
			ByteInputStream in = attribute.in();
			try {
				try {
					return converter.convert( in );
				} finally {
					in.close();
				}
			} catch ( IOException e ) {
				throw new IllegalStateException( e );
			}
		}		
	}
	
	private final Attribute find( final String name ) {
		for ( Attribute attribute : this.attributes ) {
			if ( attribute.is( name ) ) {
				return attribute;
			}
		}
		return null;
	}
	
	static final class Attribute {
		private final ConstantPool constantPool;
		private final int nameIndex;
		private final byte[] data;
		
		Attribute(
			final ConstantPool constantPool,
			final int nameIndex,
			final byte[] data )
		{
			this.constantPool = constantPool;
			this.nameIndex = nameIndex;
			this.data = data;
		}
		
		final String getName() {
			return this.constantPool.utf8( this.nameIndex );
		}
		
		final boolean is( final String name ) {
			return this.getName().equals( name );
		}
		
		final ByteInputStream in() {
			return new ByteInputStream( this.data );
		}
	}
	
	static abstract class Converter< T > {
		abstract T convert( final ByteInputStream in ) throws IOException;
	}
}
