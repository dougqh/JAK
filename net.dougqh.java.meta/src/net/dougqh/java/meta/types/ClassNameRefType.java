package net.dougqh.java.meta.types;

import java.lang.reflect.Type;

final class ClassNameRefType implements Type {
	private final String name;
	
	ClassNameRefType( final CharSequence name ) {
		this.name = name.toString();
	}
	
	final String getName() {
		return this.name;
	}
	
	@Override
	public final int hashCode() {
		return this.name.hashCode();
	}
	
	@Override
	public final boolean equals( final Object obj ) {
		if ( obj == this ) {
			return true;
		} else if ( ! ( obj instanceof ClassNameRefType ) ) {
			return false;
		} else {
			ClassNameRefType that = (ClassNameRefType)obj;
			return this.name.equals( that.name );
		}
	}
	
	@Override
	public final String toString() {
		return this.name;
	}
}
