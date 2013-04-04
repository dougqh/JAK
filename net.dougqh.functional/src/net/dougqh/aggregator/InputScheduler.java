package net.dougqh.aggregator;

public interface InputScheduler<I> {
	public abstract int numThreads();
	
	public abstract void schedule(final InputProvider<? extends I> provider) throws InterruptedException;
	
	public abstract void offer(final I result) throws InterruptedException;
}