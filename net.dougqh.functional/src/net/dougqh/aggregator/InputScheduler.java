package net.dougqh.aggregator;

public interface InputScheduler<I> {
	public abstract void schedule(final InputProvider<I> task) throws InterruptedException;
	
	public abstract void offer(final I result) throws InterruptedException;
}