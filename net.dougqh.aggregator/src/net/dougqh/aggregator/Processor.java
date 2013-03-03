package net.dougqh.aggregator;

public abstract class Processor<I, O> {
	public abstract void process(final InputChannel<I> in, final OutputChannel<O> out) throws Exception;
}