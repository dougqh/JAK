package net.dougqh.aggregator;

import java.util.Iterator;
import java.util.concurrent.Executor;

public final class Aggregator<I, O> implements Iterable<O> {
	private final InputProvider<I> rootProvider;
	private final Processor<I, O> processor;
	
	public Aggregator(final InputProvider<I> rootProvider, final Processor<I, O> processor) {
		this.rootProvider = rootProvider;
		this.processor = processor;
	}
	
	public final Iterator<O> iterator() {
		return new InThreadAggregatingIterator<I, O>(this.rootProvider, this.processor);
	}
	
	public final Iterator<O> parallelIterator(final Executor executor, final int numWorkers) {
		return new ParallelAggregatingIterator<I, O>(executor, numWorkers, this.rootProvider, this.processor);
	}
}