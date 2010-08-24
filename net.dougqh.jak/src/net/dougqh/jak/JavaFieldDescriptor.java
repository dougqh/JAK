package net.dougqh.jak;

import java.lang.reflect.Type;

//TODO: Consider consolidating with JavaVariable
public final class JavaFieldDescriptor {
	private final int flags;
	private final Type type;
	private final String name;
	
	JavaFieldDescriptor(
		final JavaFlagsBuilder flagsBuilder,
		final Type type,
		final CharSequence name )
	{
		this.flags = flagsBuilder.flags();
		this.type = type;
		this.name = name.toString();
	}
	
	final int flags() {
		return this.flags;
	}

	public final String getName() {
		return this.name;
	}
	
	public final Type getType() {
		return this.type;
	}
}
