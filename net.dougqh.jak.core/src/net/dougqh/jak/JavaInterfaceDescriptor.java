package net.dougqh.jak;

import java.lang.reflect.Type;

import net.dougqh.java.meta.types.JavaTypes;

public final class JavaInterfaceDescriptor {
	private final int flags;
	private final String name;
	private Type[] interfaceTypes = new Type[] {};
	
	JavaInterfaceDescriptor(
		final JavaModifiers flagsBuilder,
		final String name )
	{
		this.flags = flagsBuilder.flags();
		this.name = name;
	}

	public JavaInterfaceDescriptor extends_( final Type... types ) {
		this.interfaceTypes = JavaTypes.resolve( types );
		return this;
	}
	
	public final TypeDescriptor typeDescriptor() {
		return new TypeDescriptor(
			this.flags,
			this.name,
			Object.class,
			this.interfaceTypes );
	}
}
