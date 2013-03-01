package net.dougqh.jak.disassembler;

public interface JvmProcessor<T, R> {
	public abstract R process(final T value);
}
