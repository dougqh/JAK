package net.dougqh.jak.disassembler;

import java.io.IOException;

import static net.dougqh.jak.jvm.Attributes.*;

final class Attributes {
	private final ConstantPool constantPool;
	private final Attribute[] attributes;
	
	Attributes( final ConstantPool constantPool, final JvmInputStream in )
		throws IOException
	{
		this.constantPool = constantPool;
		
		int count = in.u2();
		this.attributes = new Attribute[ count ];
		
		for ( int i = 0; i < count; ++i ) {
			int nameIndex = in.u2();
			int length = in.u4();
			JvmInputStream subIn = in.readSubStream(length);
			
			this.attributes[ i ] = new Attribute(
				constantPool,
				nameIndex,
				subIn );
		}
	}
	
	final CodeAttribute getCode() {
		return this.get( 
			CODE,
			new Converter< CodeAttribute >() {
				CodeAttribute convert(
					final ConstantPool constantPool,
					final JvmInputStream in ) throws IOException
				{
					return new CodeAttribute( constantPool, in );
				}
			} );
	}
	
	private final < T > T get(
		final String name,
		final Converter< T > converter )
	{
		Attribute attribute = this.find( name );
		if ( attribute == null ) {
			return null;
		} else {
			JvmInputStream in = attribute.in();
			try {
				return converter.convert( this.constantPool, in );
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
	
	static abstract class Converter< T > {
		abstract T convert(
			final ConstantPool constantPool,
			final JvmInputStream in )
			throws IOException;
	}
}
