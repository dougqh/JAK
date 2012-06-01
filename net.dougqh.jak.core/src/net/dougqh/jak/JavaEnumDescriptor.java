package net.dougqh.jak;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import net.dougqh.java.meta.types.JavaTypes;

public final class JavaEnumDescriptor {
	private final int flags;
	private final String name;
	private Type[] interfaces = {};
	
	JavaEnumDescriptor(
		final JavaModifiers flagsBuilder,
		final String name )
	{
		this.flags = flagsBuilder.flags();
		this.name = name;
	}
	
	public final TypeDescriptor typeDescriptor() {
		return new TypeDescriptor(
			this.flags,
			this.name,
			new TypeVariable<?>[] {},
			JavaTypes.parameterize( Enum.class ).of( JavaTypes.objectTypeName( this.name ) ),
			this.interfaces );
	}
	
	public final JavaEnumDescriptor implements_( final Type... types ) {
		this.interfaces = types;
		return this;
	}
}
