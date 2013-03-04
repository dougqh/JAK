package net.dougqh.aggregator;

public abstract class Processor<I, O> {
	public abstract void process(final InputChannel<I> in, final OutputChannel<O> out) throws Exception;
	
	public Processor<I, O> filter(final Filter<O> filter) {
		return new FilteringProcessor<I, O>(this, filter);
	}
	
	public <T> Processor<I, T> transform(final Transform<O, T> transform) {
		return new TransformProcessor<I, O, T>(this, transform);
	}
	
	public <T> Processor<I, T> chain(final Processor<O, T> processor) {
		return new ChainedProcessor<I, O, T>(this, processor);
	}
}