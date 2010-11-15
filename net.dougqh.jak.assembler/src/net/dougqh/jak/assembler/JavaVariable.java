package net.dougqh.jak.assembler;

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
	
	public final String getName() {
		return this.name;
	}
	
	public final Type getType() {
		return this.type;
	}
}
