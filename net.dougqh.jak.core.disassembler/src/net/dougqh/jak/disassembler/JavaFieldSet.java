package net.dougqh.jak.disassembler;

import net.dougqh.jak.JavaField;

public interface JavaFieldSet<T extends JavaField> extends Iterable<T>{
	public abstract boolean isEmpty();
	
	public abstract int size();
	
	public T get(final int index);
}
