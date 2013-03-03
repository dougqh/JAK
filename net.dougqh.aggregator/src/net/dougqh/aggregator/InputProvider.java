package net.dougqh.aggregator;


public interface InputProvider<I> {
	public abstract void run(final InputScheduler<I> scheduler) throws Exception;
}