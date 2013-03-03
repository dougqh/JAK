package net.dougqh.aggregator;

public abstract class InputProcessor<I, O> {
	public abstract void process(final InputChannel<I> in, final OutputChannel<O> out) throws Exception;
}