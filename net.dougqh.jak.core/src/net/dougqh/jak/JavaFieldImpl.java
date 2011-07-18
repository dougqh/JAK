package net.dougqh.jak;

import java.lang.reflect.Type;

final class JavaFieldImpl extends JavaField {
	private final int flags;
	private final Type type;
	private final String name;
	
	JavaFieldImpl(
		final JavaFlagsBuilder flagsBuilder,
		final Type type,
		final CharSequence name )
	{
		this.flags = flagsBuilder.flags();
		this.type = type;
		this.name = name.toString();
	}
	
	public final int getFlags() {
		return this.flags;
	}

	public final String getName() {
		return this.name;
	}
	
	public final Type getType() {
		return this.type;
	}
}
