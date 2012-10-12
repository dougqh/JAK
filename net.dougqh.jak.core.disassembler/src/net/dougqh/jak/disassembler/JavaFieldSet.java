package net.dougqh.jak.disassembler;

import net.dougqh.jak.JavaField;
import net.dougqh.jak.JavaFilter;

public interface JavaFieldSet<T extends JavaField> extends Iterable<T>{
	public abstract boolean isEmpty();
	
	public abstract int size();
	
	public abstract T get(final int index);
	
	public abstract JavaFieldSet<T> filter(
		final JavaFilter<? super T> predicate);
}
