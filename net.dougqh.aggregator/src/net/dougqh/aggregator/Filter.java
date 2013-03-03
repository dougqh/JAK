package net.dougqh.aggregator;

public interface Filter<I> {
	public abstract boolean matches(final I value);
}
