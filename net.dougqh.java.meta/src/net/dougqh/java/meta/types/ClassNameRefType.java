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
	public final String toString() {
		return this.name;
	}
}
