package net.dougqh.jak;

import java.lang.reflect.Type;

import net.dougqh.java.meta.types.JavaTypes;

public final class JavaVariable {
	private final Type type;
	private final String name;
	
	JavaVariable(
		final Type type,
		final String name )
	{
		this.type = JavaTypes.resolve( type );
		this.name = name;
	}
	
	public final String name() {
		return this.name;
	}
	
	public final Type type() {
		return this.type;
	}
}
