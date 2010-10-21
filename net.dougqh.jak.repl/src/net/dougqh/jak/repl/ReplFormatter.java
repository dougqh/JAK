package net.dougqh.jak.repl;

import java.lang.reflect.Type;

import net.dougqh.jak.ConstantEntry;
import net.dougqh.jak.JavaFieldDescriptor;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.java.meta.types.JavaTypes;

final class ReplFormatter {
	private ReplFormatter() {}
	
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
		if ( type.equals( CharSequence.class ) ) {
			return "String";
		} else if ( type instanceof Class ) {
			Class< ? > aClass = (Class< ? >)type;
			return aClass.getCanonicalName();
		} else {
			return JavaTypes.getRawClassName( type );
		}
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
		} else if ( value instanceof JavaFieldDescriptor ) {
			JavaFieldDescriptor field = (JavaFieldDescriptor)value;
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

	private static String format( final JavaFieldDescriptor field ) {
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
