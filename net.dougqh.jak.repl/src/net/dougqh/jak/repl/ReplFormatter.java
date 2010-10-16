package net.dougqh.jak.repl;

import java.lang.reflect.Type;

import net.dougqh.jak.ConstantEntry;
import net.dougqh.jak.JavaFieldDescriptor;
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
			Class< ? > type = (Class< ?>)value;
			return getDisplayName( type );
		} else if ( value instanceof ConstantEntry ) {
			ConstantEntry entry = (ConstantEntry)value;
			return entry.index() + ":" + format( entry.value() );
		} else if ( value instanceof String ) {
			String string = (String)value;
			return ReplArgument.STRING_QUOTE + string + ReplArgument.STRING_QUOTE;
		} else if ( value instanceof JavaFieldDescriptor ) {
			JavaFieldDescriptor field = (JavaFieldDescriptor)value;
			return field.getName() + ":" + format( field.getType() );
		} else {
			return value.toString();
		}
	}
}
