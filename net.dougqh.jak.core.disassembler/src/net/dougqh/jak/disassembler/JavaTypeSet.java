package net.dougqh.jak.disassembler;

import net.dougqh.functional.Filter;

public abstract class JavaTypeSet<T extends JavaType> implements Iterable<T> {
	public abstract T get(final String name);
	
	public abstract JavaClass getClass(final String name);
	
	public abstract JavaInterface getInterface(final String name);
	
	public abstract JavaEnum getEnum(final String name);
	
	public abstract JavaAnnotation getAnnotation(final String name);
	
	public abstract JavaTypeSet<T> filter(final Filter<? super T> filter);
}
