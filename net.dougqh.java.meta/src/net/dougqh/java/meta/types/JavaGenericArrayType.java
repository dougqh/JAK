package net.dougqh.java.meta.types;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

public final class JavaGenericArrayType implements GenericArrayType {
	private final Type componentType;
	
	public JavaGenericArrayType( final Type componentType ) {
		this.componentType = componentType;
	}
	
	//TODO: Implement hashCode
	
	@Override
	public final boolean equals( final Object obj ) {
		if ( obj == this ) {
			return true;
		} else if ( ! ( obj instanceof GenericArrayType ) ) {
			return false;
		} else {
			GenericArrayType that = (GenericArrayType)obj;
			return this.getGenericComponentType().equals( that.getGenericComponentType() );
		}
	}
	
	@Override
	public final Type getGenericComponentType() {
		return this.componentType;
	}
	
	@Override
	public final String toString() {
		return this.componentType.toString() + "[]";
	}
}
