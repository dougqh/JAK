package net.dougqh.jak.disassembler;

public interface JakReader {
	public abstract Iterable< JavaType > list();
	
	public abstract <T extends JavaType, R> Iterable<R> each(
			final JvmProcessor<T, R> proccessor);
}
