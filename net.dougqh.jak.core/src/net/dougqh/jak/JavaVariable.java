package net.dougqh.jak;

import java.lang.reflect.Type;

public final class JavaVariable {
	private final Type type;
	private final String name;
	
	JavaVariable(
		final Type type,
		final String name )
	{
		this.type = type;
		this.name = name;
	}
	
	public final String getName() {
		return this.name;
	}
	
	public final Type getType() {
		return this.type;
	}
	
	@Override
	public final String toString() {
		return this.name + ":" + this.type;
	}
}
