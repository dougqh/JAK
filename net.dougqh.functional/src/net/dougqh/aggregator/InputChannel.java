package net.dougqh.aggregator;

public interface InputChannel<I> {
	public abstract I poll();
}