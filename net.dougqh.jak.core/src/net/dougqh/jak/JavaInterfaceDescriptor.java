package net.dougqh.jak;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public final class JavaInterfaceDescriptor {
	private static final Type[] EMPTY_INTERFACES = {};
	private static final TypeVariable<?>[] EMPTY_TYPE_VARS = {};
	
	private final int flags;
	private final String name;
	private Type[] interfaceTypes = EMPTY_INTERFACES;
	private TypeVariable<?>[] typeVars = EMPTY_TYPE_VARS;
	
	JavaInterfaceDescriptor(
		final JavaModifiers flagsBuilder,
		final String name )
	{
		this.flags = flagsBuilder.flags();
		this.name = name;
	}

	public JavaInterfaceDescriptor extends_( final Type... types ) {
		this.interfaceTypes = types.clone();
		return this;
	}
	
	public final JavaInterfaceDescriptor parameterize( final TypeVariable<?>... typeVars ) {
		if ( this.typeVars.length != 0 ) {
			throw new IllegalStateException( "Type variables already set" );
		}
		this.typeVars = typeVars.clone();
		
		return this;
	}
	
	public final TypeDescriptor typeDescriptor() {
		return new TypeDescriptor(
			this.flags,
			this.name,
			this.typeVars,
			Object.class,
			this.interfaceTypes );
	}
}
