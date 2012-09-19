package net.dougqh.jak.disassembler;

import static net.dougqh.jak.jvm.ConstantPoolConstants.*;

import java.io.IOException;

final class ConstantPool {
	private final Object[] constants;
	
	ConstantPool( final JvmInputStream in ) throws IOException {		
		int count = in.u2();
		this.constants = new Object[ count ];
		
		for ( int index = 1; index < count; ++index ) {
			byte tag = in.u1();
			
			switch ( tag ) {
				case CLASS: {
					this.constants[ index ] = readClassInfo( in );
					break;
				}
				
				case FIELD_REF: {
					this.constants[ index ] = readFieldRef( in );
					break;
				}
				
				case METHOD_REF: {
					this.constants[ index ] = readMethodRef( in );
					break;
				}
				
				case INTERFACE_METHOD_REF: {
					this.constants[ index ] = readInterfaceMethodRef( in );
					break;
				}
				
				case STRING: {
					this.constants[ index ] = readString( in );
					break;
				}
				
				case INTEGER: {
					this.constants[ index ] = readInteger( in );
					break;
				}
				
				case FLOAT: {
					this.constants[ index ] = readFloat( in );
					break;
				}
				
				case LONG: {
					this.constants[ index ] = readLong( in );
					++index;
					break;
				}
				
				case DOUBLE: {
					this.constants[ index ] = readDouble( in );
					++index;
					break;
				}
				
				case NAME_AND_TYPE: {
					this.constants[ index ] = readNameAndType( in );
					break;
				}
				
				case UTF8: {
					this.constants[ index ] = readUtf8( in );
					break;
				}
			}
		}
	}
	
	public final String classValue( final int index ) {
		String jvmClassName = this.utf8( this.index( index ) );
		return jvmClassName.replace( '/', '.' );
	}
	
	public final int intValue( final int index ) {
		return (Integer)this.constants[ index ];
	}
	
	public final float floatValue( final int index ) {
		return (Float)this.constants[ index ];
	}
	
	public final long longValue( final int index ) {
		return (Long)this.constants[ index ];
	}
	
	public final double doubleValue( final int index ) {
		return (Double)this.constants[ index ];
	}
	
	public final int index( final int index ) {
		return ( (Reference)this.constants[ index ] ).index;
	}
	
	public final int firstIndex( final int index ) {
		return ( (References)this.constants[ index ] ).firstIndex;
	}

	public final int secondIndex( final int index ) {
		return ( (References)this.constants[ index ] ).firstIndex;
	}
	
	public final String utf8( final int index ) {
		return (String)this.constants[ index ];
	}
	
	private static final Reference readClassInfo( final JvmInputStream in ) throws IOException {
		int classIndex = in.u2();
		return new Reference( classIndex );
	}
	
	private static final References readFieldRef( final JvmInputStream in ) throws IOException {
		int classIndex = in.u2();
		int nameAndTypeIndex = in.u2();
		return new References( classIndex, nameAndTypeIndex );
	}
	
	private static final References readMethodRef( final JvmInputStream in ) throws IOException {
		int classIndex = in.u2();
		int nameAndTypeIndex = in.u2();
		return new References( classIndex, nameAndTypeIndex );
	}

	private static final References readInterfaceMethodRef( final JvmInputStream in ) throws IOException {
		int classIndex = in.u2();
		int nameAndTypeIndex = in.u2();
		
		return new References( classIndex, nameAndTypeIndex );
	}
	
	private static final Reference readString( final JvmInputStream in ) throws IOException {
		int utf8Index = in.u2();
		return new Reference( utf8Index );
	}
	
	private static final Integer readInteger( final JvmInputStream in ) throws IOException {
		return in.u4();
	}
	
	private static final Float readFloat( final JvmInputStream in ) throws IOException {
		return in.u4Float();
	}
	
	private final Long readLong( final JvmInputStream in ) throws IOException {
		return in.u8();
	}
	
	private static final Double readDouble( final JvmInputStream in ) throws IOException {
		return in.u8Double();
	}
	
	private static final References readNameAndType( final JvmInputStream in ) throws IOException {
		int nameIndex = in.u2();
		int descriptorIndex = in.u2();
		
		return new References( nameIndex, descriptorIndex );
	}
	
	private static final String readUtf8( final JvmInputStream in ) throws IOException {
		int length = in.u2();
		
		return in.utf8( length );
	}

	static final class Reference {
		final int index;
		
		Reference( final int index ) {
			this.index = index;
		}
	}
	
	static final class References {
		final int firstIndex;
		final int secondIndex;
		
		References(
			final int firstIndex,
			final int secondIndex )
		{
			this.firstIndex = firstIndex;
			this.secondIndex = secondIndex;
		}
	}
}
