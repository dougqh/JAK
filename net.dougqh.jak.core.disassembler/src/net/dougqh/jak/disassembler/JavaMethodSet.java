package net.dougqh.jak.disassembler;

public interface JavaMethodSet<T extends JavaMethod> extends Iterable<T>{
	public abstract boolean isEmpty();
	
	public abstract int size();
	
	public abstract T get(final int index);
}
