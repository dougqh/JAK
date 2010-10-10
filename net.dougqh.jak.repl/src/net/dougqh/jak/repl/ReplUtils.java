package net.dougqh.jak.repl;

import java.lang.reflect.Type;

import net.dougqh.java.meta.types.JavaTypes;

final class ReplUtils {
	private ReplUtils() {}
	
	public static final String getDisplayName( final Type type ) {
		return JavaTypes.getRawClassName( type );
	}
}
