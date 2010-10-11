package net.dougqh.jak.repl;

import java.lang.reflect.Type;

import net.dougqh.java.meta.types.JavaTypes;

final class ReplUtils {
	private ReplUtils() {}
	
	public static final String getDisplayName( final Type type ) {
		if ( type.equals( CharSequence.class ) ) {
			return "String";
		} else if ( type instanceof Class ) {
			Class< ? > aClass = (Class< ? >)type;
			return aClass.getSimpleName();
		} else {
			return JavaTypes.getRawClassName( type );
		}
	}
}
