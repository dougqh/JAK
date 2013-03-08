package net.dougqh.aggregator;


public interface InputProvider<I> {
	public abstract void run(final InputScheduler<? super I> scheduler) throws Exception;
}