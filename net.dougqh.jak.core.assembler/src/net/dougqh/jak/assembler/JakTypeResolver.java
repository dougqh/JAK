package net.dougqh.jak.assembler;

import java.lang.reflect.Type;

import net.dougqh.java.meta.types.JavaTypes;

public abstract class JakTypeResolver {
	public final Type resolve( final Type type ) {
		if ( type instanceof ThisType ) {
			return this.thisType(); 
		} else if ( type instanceof SuperType ) {
			return this.superType();
		} else {
			return JavaTypes.resolve( type );
		}
	}
	
	protected abstract Type thisType();
	
	protected abstract Type superType();
}
