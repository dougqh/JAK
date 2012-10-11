package net.dougqh.jak.disassembler;

public interface JavaFilter<T> {
	public abstract boolean matches( final T obj );
}
