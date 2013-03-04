package net.dougqh.aggregator;

public abstract class Transform<I, O> {
	public abstract O transform(final I input) throws Exception;
}
