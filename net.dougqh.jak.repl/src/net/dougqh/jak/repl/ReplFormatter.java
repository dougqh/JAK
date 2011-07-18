package net.dougqh.jak.repl;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaField;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.jvm.assembler.ConstantEntry;
import net.dougqh.jak.types.Reference;
import net.dougqh.java.meta.types.JavaTypes;

final class ReplFormatter {
	private ReplFormatter() {}
	
	public static final < T extends Enum< T > & ReplEnum< T > > T parse(
		final Class< T > enumClass,
		final String value )
	{
		for ( T enumConstant : enumClass.getEnumConstants() ) {
			if ( enumConstant.id().equals( value ) ) {
				return enumConstant;
			}
		}
		throw new IllegalArgumentException();
	}
	
	public static final String getShortDisplayName( final Type type ) {
		String fullName = getDisplayName( type );
		
		int lastDotPos = fullName.lastIndexOf( '.' );
		if ( lastDotPos == -1 ) {
			return fullName;
		} else {
			return fullName.substring( lastDotPos + 1 );
		}
	}
	
	public static final String getDisplayName( final Type type ) {
		if ( type.equals( Reference.class ) ) {
			return "<Reference>";
		} else if ( type instanceof Class ) {
			Class< ? > aClass = (Class< ? >)type;
			
			if ( ReplEnum.class.isAssignableFrom( aClass ) ) {
				@SuppressWarnings( "unchecked" )
				Class< ? extends ReplEnum< ? > > replEnumClass = (Class< ? extends ReplEnum< ? > >)aClass;
				return getEnumName( replEnumClass );
			} else {
				return aClass.getCanonicalName();
			}
		} else {
			return JavaTypes.getRawClassName( type );
		}
	}

	private static final String getEnumName( Class< ? extends ReplEnum< ? > > aClass) {
		StringBuilder builder = new StringBuilder();
		boolean isFirst = true;
		for ( ReplEnum< ? > enumConstant : aClass.getEnumConstants() ) {
			if ( isFirst ) {
				isFirst = false;
			} else {
				builder.append( "|" );
			}
			builder.append( enumConstant.id() );
		}
		return builder.toString();
	}
	
	public static final String format( final Object value ) {
		if ( value == null ) {
			return "null";
		} else if ( value instanceof Class ) {
			return format( (Class< ?>)value );
		} else if ( value instanceof ConstantEntry ) {
			return format( (ConstantEntry)value );
		} else if ( value instanceof String ) {
			return format( (String)value );
		} else if ( value instanceof JavaField ) {
			JavaField field = (JavaField)value;
			return format( field );
		} else if ( value instanceof JavaMethodDescriptor ) {
			return format( (JavaMethodDescriptor)value );
		} else {
			return value.toString();
		}
	}

	private static final String format( final Class<?> type ) {
		return getDisplayName( type );
	}

	private static String format( final ConstantEntry entry ) {
		return entry.index() + ":" + format( entry.value() );
	}

	private static String format( final String string ) {
		return ReplArgument.STRING_QUOTE + string + ReplArgument.STRING_QUOTE;
	}

	private static String format( final JavaField field ) {
		return field.getName() + ":" + format( field.getType() );
	}
	
	private static final String format( final JavaMethodDescriptor method ) {
		StringBuilder builder = new StringBuilder();
		builder.append( method.getName() );
		builder.append( '(' );
		
		boolean isFirst = true;
		for ( Type type : method.getArgumentTypes() ) {
			if ( isFirst ) {
				isFirst = false;
			} else {
				builder.append( ',' );
			}
			builder.append( format( type ) );
		}
		
		builder.append( "):" );
		builder.append( format( method.getReturnType() ) );
		
		return builder.toString();
	}
}
