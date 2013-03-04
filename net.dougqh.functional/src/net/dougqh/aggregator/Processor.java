package net.dougqh.aggregator;

import net.dougqh.functional.Filter;
import net.dougqh.functional.Transform;

public abstract class Processor<I, O> {
	public abstract void process(
		final InputChannel<? extends I> in,
		final OutputChannel<? super O> out
	) throws Exception;
	
	public Processor<I, O> filter(final Filter<? super O> filter) {
		return new FilteringProcessor<I, O>(this, filter);
	}
	
	public <T> Processor<I, T> transform(final Transform<? super O, ? extends T> transform) {
		return new TransformProcessor<I, O, T>(this, transform);
	}
	
	public <T> Processor<I, T> chain(final Processor<O, T> processor) {
		return new ChainedProcessor<I, O, T>(this, processor);
	}
}