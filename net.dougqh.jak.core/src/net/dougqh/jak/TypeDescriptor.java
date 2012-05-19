package net.dougqh.jak;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;


public final class TypeDescriptor {
	private final int flags;
	private final String name;
	private final TypeVariable<?>[] typeVars;
	private final Type parentType;
	private final Type[] interfaceTypes;
	
	TypeDescriptor(
		final int flags,
		final String name,
		final TypeVariable<?>[] typeVars,
		final Type parentType,
		final Type[] interfaceTypes )
	{
		this.flags = flags;
		this.name = name;
		this.typeVars = typeVars;
		this.parentType = parentType;
		this.interfaceTypes = interfaceTypes;
	}
	
	public final JavaVersion version() {
		return JavaVersion.getDefault();
	}
	
	public final int flags() {
		return this.flags;
	}
	
	public final String name() {
		return this.name;
	}
	
	public final Type parentType() {
		return this.parentType;
	}
	
	public final Type[] interfaceTypes() {
		return this.interfaceTypes;
	}
	
	public final TypeVariable<?>[] typeVars() {
		return this.typeVars;
	}
	
	public final TypeDescriptor qualify( final String packageName ) {
		return new TypeDescriptor(
			this.flags,
			packageName + '.' + this.name,
			this.typeVars,
			this.parentType,
			this.interfaceTypes );
	}
	
	public final TypeDescriptor innerType(
		final String className,
		final int additionalFlags )
	{
		return new TypeDescriptor(
			this.flags | additionalFlags,
			className + '$' + this.name,
			this.typeVars,
			this.parentType,
			this.interfaceTypes );
	}
}
