package net.dougqh.jak;

import java.lang.reflect.Type;

import net.dougqh.java.meta.types.JavaTypes;

public final class JavaClassDescriptor {
	private final int flags;
	private final String name;
	private Type parentType = Object.class;
	private Type[] interfaceTypes = new Type[] {};
	
	JavaClassDescriptor(
		final JavaModifiers flagsBuilder,
		final String name )
	{
		this.flags = flagsBuilder.flags();
		this.name = name;
	}
		
	public final JavaClassDescriptor extends_( final String name ) {
		return this.extends_( JavaTypes.objectTypeName( name ) );
	}
	
	public final JavaClassDescriptor extends_( final Type type ) {
		this.parentType = JavaTypes.resolve( type );
		return this;
	}
	
	public final JavaClassDescriptor implements_( final Type... types ) {
		this.interfaceTypes = JavaTypes.resolve( types );
		return this;
	}
	
	public final TypeDescriptor typeDescriptor() {
		return new TypeDescriptor(
			this.flags,
			this.name,
			this.parentType,
			this.interfaceTypes );
	}
}
