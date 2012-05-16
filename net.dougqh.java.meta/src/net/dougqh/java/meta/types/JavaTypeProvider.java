package net.dougqh.java.meta.types;

import java.lang.reflect.Type;

public interface JavaTypeProvider extends Type {
	//TODO: Should return a Type[]
	//This is needed to provide a correct definition for 
	//both UnionType-s and JavaTypeVariable-s.
	public abstract Type get();
}
