package net.dougqh.aggregator;

public interface OutputChannel<O> {
	public abstract void offer(final O offer);
}