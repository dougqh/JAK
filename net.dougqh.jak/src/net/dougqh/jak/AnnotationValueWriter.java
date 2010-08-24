package net.dougqh.jak;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import net.dougqh.java.meta.types.JavaTypes;

final class AnnotationValueWriter {
//	private static final char BOOLEAN = 'Z';
//	private static final char CHAR = 'C';
//	private static final char BYTE ='B';
//	private static final char SHORT = 'S';
//	private static final char INT = 'I';
//	private static final char LONG = 'J';
//	private static final char FLOAT = 'F';
//	private static final char DOUBLE = 'D';
//	
//	private static final char STRING = 's';
//	private static final char ENUM = 'e';
//	private static final char CLASS = 'c';
//	private static final char ANNOTATION = '@';
//	private static final char ARRAY = '[';
//	
//	private final ConstantPool constantPool;
//	private final ByteStream byteOut;
	
	AnnotationValueWriter( final ConstantPool constantPool ) {
//		this.constantPool = constantPool;
//		this.byteOut = new ByteStream( 256 );
	}
	
	final void write(
		final Type targetType,
		final Object value )
	{
		Class< ? > rawTargetClass = JavaTypes.getRawClass( targetType );
		
		if ( boolean.class.isAssignableFrom( rawTargetClass ) ) {
			this.writeBoolean( (Boolean)value );
		} else if ( byte.class.isAssignableFrom( rawTargetClass ) ) {
			this.writeByte( (Number)value );
		} else if ( char.class.isAssignableFrom( rawTargetClass ) ) {
			this.writeCharacter( (Character)value );
		} else if ( short.class.isAssignableFrom( rawTargetClass ) ) {
			this.writeShort( (Number)value );
		} else if ( int.class.isAssignableFrom( rawTargetClass ) ) {
			this.writeInteger( (Number)value );
		} else if ( long.class.isAssignableFrom( rawTargetClass ) ) {
			this.writeLong( (Number)value );
		} else if ( float.class.isAssignableFrom( rawTargetClass ) ) {
			this.writeFloat( (Number)value );
		} else if ( double.class.isAssignableFrom( rawTargetClass ) ) {
			this.writeDouble( (Number)value );
		} else if ( String.class.isAssignableFrom( rawTargetClass ) ) {
			this.writeString( (CharSequence)value );
		} else if ( Enum.class.isAssignableFrom( rawTargetClass ) ) {
			this.writeEnum( (Enum< ? >)value );
		} else if ( Annotation.class.isAssignableFrom( rawTargetClass ) ) {
			this.writeAnnotation( (Annotation)value );
		} else if ( rawTargetClass.isArray() ) {
			this.writeArray( value );
		} else {
			throw new IllegalStateException( "Invalid type: " + targetType.toString() );
		}
	}
	
	final void writeBoolean( final boolean value ) {
		
	}
	
	final void writeByte( final Number value ) {
		
	}
	
	final void writeCharacter( final char value ) {
		
	}
	
	final void writeShort( final Number value ) {
		
	}
	
	final void writeInteger( final Number value ) {
		
	}
	
	final void writeLong( final Number value ) {
		
	}
	
	final void writeFloat( final Number value ) {
		
	}
	
	final void writeDouble( final Number value ) {
		
	}
	
	final void writeString( final CharSequence value ) {
		
	}
	
	final void writeEnum( final Enum< ? > value ) {
		
	}
	
	final void writeAnnotation( final Annotation value ) {
		
	}
	
	final void writeArray( final Object array ) {
		
	}
}
