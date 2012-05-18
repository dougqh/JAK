package net.dougqh.jak.assembler;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import net.dougqh.java.meta.types.JavaTypes;

public abstract class TypeResolver {
	private final TypeResolver parent;
	
	public TypeResolver() {
		this( null );
	}
	
	public TypeResolver( final TypeResolver parent ) {
		this.parent = parent;
	}
	
	public final Type resolve( final Type type ) {
		if ( type instanceof TypeVariable ) {
			TypeVariable<?> typeVar = (TypeVariable<?>)type;
			return this.resolveDeclaration( typeVar );
		} else if ( type instanceof ThisType ) {
			return this.thisType(); 
		} else if ( type instanceof SuperType ) {
			return this.superType();
		} else {
			return JavaTypes.resolve( type );
		}
	}
	
	protected TypeVariable<?> resolveDeclaration( final TypeVariable<?> typeVar ) {
		TypeResolver parent = this.parent;
		if ( parent != null ) {
			return parent.resolveDeclaration( typeVar );
		} else {
			return typeVar;
		}
	}
	
	protected Type thisType() {
		TypeResolver parent = this.parent;
		if ( parent != null ) {
			return parent.thisType();
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	protected Type superType() {
		TypeResolver parent = this.parent;
		if ( parent != null ) {
			return parent.thisType();
		} else {
			throw new UnsupportedOperationException();
		}
	}
}
