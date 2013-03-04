package net.dougqh.jak.disassembler;

import net.dougqh.functional.Filter;
import net.dougqh.jak.JavaField;

public interface JavaFieldSet<T extends JavaField> extends Iterable<T>{
	public abstract boolean isEmpty();
	
	public abstract int size();
	
	public abstract T get(final int index);
	
	public abstract JavaFieldSet<T> filter(final Filter<? super T> predicate);
}
