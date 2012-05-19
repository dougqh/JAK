package net.dougqh.jak;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import net.dougqh.java.meta.types.JavaTypes;

public final class JavaClassDescriptor {
	private static final Type[] EMPTY_INTERFACES = {};
	private static final TypeVariable<?>[] EMPTY_TYPE_VARS = {};
	
	private final int flags;
	private final String name;
	private Type parentType = Object.class;
	private Type[] interfaceTypes = EMPTY_INTERFACES;
	private TypeVariable<?>[] typeVars = EMPTY_TYPE_VARS;

	JavaClassDescriptor(
		final JavaModifiers modifiers,
		final String name )
	{
		this.flags = modifiers.flags();
		this.name = name;
	}
	
	public final JavaClassDescriptor parameterize( final TypeVariable<?>... typeVars ) {
		if ( this.typeVars.length != 0 ) {
			throw new IllegalStateException( "Type variables already set" );
		}
		this.typeVars = typeVars.clone();
		
		return this;
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
			this.typeVars,
			this.parentType,
			this.interfaceTypes );
	}
}
