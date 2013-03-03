package net.dougqh.aggregator;

public interface Transform<I, O> {
	public O transform(final I input) throws Exception;
}
