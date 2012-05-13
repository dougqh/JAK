package net.dougqh.java.meta.types;

import java.lang.reflect.Type;

public interface JavaTypeProvider extends Type {
	public abstract Type get();
}
