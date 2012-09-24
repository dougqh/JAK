package net.dougqh.jak.disassembler;

import java.io.IOException;
import java.lang.reflect.Type;

import net.dougqh.jak.Jak;
import net.dougqh.jak.JavaField;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.java.meta.types.JavaTypes;
import static net.dougqh.jak.jvm.ConstantPoolConstants.*;

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
	
	public final String typeName( final int index ) {
		String jvmClassName = this.utf8( this.index( index ) );
		return jvmClassName.replace( '/', '.' );
	}
	
	public final Type type( final int index ) {
		return JavaTypes.objectTypeName( this.typeName( index ) );
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
	
	public final String utf8( final int index ) {
		return (String)this.constants[ index ];
	}
	
	public final Type targetType( final int refIndex ) {
		return this.type( this.firstIndex( refIndex ) );
	}
	
	public final JavaField field( final int refIndex ) {
		int nameAndTypeIndex = this.secondIndex(refIndex);
		
		int nameIndex = this.firstIndex(nameAndTypeIndex);
		int typeIndex = this.secondIndex(nameAndTypeIndex);
		
		return Jak.field(this.type(typeIndex), this.utf8(nameIndex));
	}
	
	public final JavaMethodDescriptor methodDescriptor( final int refIndex ) {
		int nameAndTypeIndex = this.secondIndex(refIndex);
		
		int nameIndex = this.firstIndex(nameAndTypeIndex);
		int signatureIndex = this.secondIndex(nameAndTypeIndex);
		
		String name = this.utf8(nameIndex);
		String signature = this.utf8(signatureIndex);
		
		if ( ! signature.equals("()V" ) ) {
			throw new IllegalStateException("Not implemented");
		}
		
		return Jak.method(void.class, name);
	}

	private final int index( final int index ) {
		return ( (SingularReference)this.constants[ index ] ).index;
	}
	
	private final int firstIndex( final int index ) {
		return ( (DualReference)this.constants[ index ] ).firstIndex;
	}

	private final int secondIndex( final int index ) {
		return ( (DualReference)this.constants[ index ] ).secondIndex;
	}
	
	private static final SingularReference readClassInfo( final JvmInputStream in ) throws IOException {
		int classIndex = in.u2();
		return new SingularReference( classIndex );
	}
	
	private static final DualReference readFieldRef( final JvmInputStream in ) throws IOException {
		int classIndex = in.u2();
		int nameAndTypeIndex = in.u2();
		return new DualReference( classIndex, nameAndTypeIndex );
	}
	
	private static final DualReference readMethodRef( final JvmInputStream in ) throws IOException {
		int classIndex = in.u2();
		int nameAndTypeIndex = in.u2();
		return new DualReference( classIndex, nameAndTypeIndex );
	}

	private static final DualReference readInterfaceMethodRef( final JvmInputStream in ) throws IOException {
		int classIndex = in.u2();
		int nameAndTypeIndex = in.u2();
		
		return new DualReference( classIndex, nameAndTypeIndex );
	}
	
	private static final SingularReference readString( final JvmInputStream in ) throws IOException {
		int utf8Index = in.u2();
		return new SingularReference( utf8Index );
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
	
	private static final DualReference readNameAndType( final JvmInputStream in ) throws IOException {
		int nameIndex = in.u2();
		int descriptorIndex = in.u2();
		
		return new DualReference( nameIndex, descriptorIndex );
	}
	
	private static final String readUtf8( final JvmInputStream in ) throws IOException {
		int length = in.u2();
		
		return in.utf8( length );
	}

	/**
	 * Used for any single reference entry - like class entry
	 */
	static final class SingularReference {
		final int index;
		
		SingularReference( final int index ) {
			this.index = index;
		}
	}
	
	/**
	 * Used for any dual reference entry - like...
	 *  - method ref
	 *  - interface method ref
	 *  - field ref
	 *  - name and type pair
	 */
	static final class DualReference {
		final int firstIndex;
		final int secondIndex;
		
		DualReference(
			final int firstIndex,
			final int secondIndex )
		{
			this.firstIndex = firstIndex;
			this.secondIndex = secondIndex;
		}
	}
}
