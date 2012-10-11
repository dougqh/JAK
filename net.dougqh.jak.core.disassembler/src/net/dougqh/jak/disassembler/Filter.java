package net.dougqh.jak.disassembler;

public interface Filter<T> {
	public abstract boolean matches( final T obj );
}
