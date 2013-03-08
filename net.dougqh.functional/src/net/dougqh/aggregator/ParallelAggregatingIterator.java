package net.dougqh.aggregator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

final class ParallelAggregatingIterator<I, O> implements Iterator<O> {
	private static final End END = new End();
	
	private final Executor executor;
	
	private final Processor<? super I, ? extends O> processor;
	
	private final ConditionallyBlockingQueue<InputProvider<? extends I>> providerQueue = 
		new ConditionallyBlockingQueue<InputProvider<? extends I>>();
	
	private final ConditionallyBlockingQueue<O> resultQueue = new ConditionallyBlockingQueue<O>();
	
	private final AtomicInteger activeCount = new AtomicInteger(0);
	
	private Object value = null;
	private Throwable cause = null;
	
	ParallelAggregatingIterator(
		final Executor executor,
		final int numWorkers,
		final InputProvider<? extends I> rootProvider,
		final Processor<? super I, ? extends O> processor)
	{
		this.executor = executor;
		this.processor = processor;
		
		for ( int i = 0; i < numWorkers; ++i ) {
			this.scheduleWorker();
		}
		
		try {
			this.providerQueue.offer(rootProvider);
		} catch (InterruptedException e) {
			// Given that the queue was just created it should be at capacity
			throw new IllegalStateException(e);
		}
	}
	
	private final void scheduleWorker() {
		this.executor.execute(new BackgroundWorkerRunnable());
	}
	
	@Override
	public final boolean hasNext() {
		this.loadNext();
		
		return ! ( this.value instanceof End );
	}
	
	@Override
	public final O next() {
		this.loadNext();
		
		if ( this.value instanceof End ) {
			throw new NoSuchElementException();
		}
		
		@SuppressWarnings("unchecked")
		O castedValue = (O)this.value;
		this.clear();
		return castedValue;
	}
	
	@Override
	public final void remove() {
		throw new UnsupportedOperationException();
	}
	
	final void loadNext() {
		if ( this.value == null ) {
			this.value = this.awaitNext();
		}
	}
	
	final Object awaitNext() {
		this.checkForException();
		O result = this.resultQueue.poll();
		if ( result != null ) {
			return result;
		}
		
		while ( this.resultQueue.isEmpty() ) {
			InputProvider<? extends I> provider = this.providerQueue.poll();
			if ( provider == null && this.activeCount.get() == 0 ) {
				return END;
			}
			
			this.runNow(provider);
		}
		
		this.checkForException();
		return this.resultQueue.poll();
	}
	
	private final void clear() {
		this.value = null;
	}
	
	final void exception(final Throwable cause) {
		this.cause = cause;
	}
	
	final void checkForException() {
		if ( this.cause != null ) {
			throw new IllegalStateException(this.cause);
		}
	}
	
	final boolean isDone() {
		return this.resultQueue.isEmpty() && this.activeCount.get() == 0;
	}
	
	final void runNow(final InputProvider<? extends I> provider) {
		this.providerQueue.suspendLimit();
		try {
			this.resultQueue.suspendLimit();
			try {
				this.run(provider);
			} finally {
				this.resultQueue.restoreLimit();
			}
		} finally {
			this.providerQueue.restoreLimit();
		}
	}
	
	final void run(final InputProvider<? extends I> provider) {
		this.activeCount.incrementAndGet();
		try {
			SchedulerImpl schedulerImpl = new SchedulerImpl();
			provider.run(schedulerImpl);
			schedulerImpl.flush();
		} catch ( Exception e ) {
			this.exception(e);
			
			this.checkForException();
		} finally {
			this.activeCount.decrementAndGet();
		}
	}
	
	private final class SchedulerImpl implements InputScheduler<I> {
		private final ConcurrentLinkedQueue<I> inputQueue = new ConcurrentLinkedQueue<I>();
		
		@Override
		public final int numThreads() {
			return 1;
		}
		
		@Override
		public final void schedule(final InputProvider<? extends I> provider) throws InterruptedException {
			ParallelAggregatingIterator.this.providerQueue.offer(provider);
		}
		
		public final void offer(final I result) throws InterruptedException {
			this.inputQueue.offer(result);
		}
		
		public final void flush() throws Exception {
			ParallelAggregatingIterator.this.processor.process(
				new QueueBasedInputChannel<I>(this.inputQueue),
				new OutputChannel<O>() {
					public final void offer(final O input) {
						try {
							ParallelAggregatingIterator.this.resultQueue.offer(input);
						} catch (InterruptedException e) {
							ParallelAggregatingIterator.this.exception(e);
						}
					}
				}
			);
		}
	}
	
	private final class BackgroundWorkerRunnable implements Runnable {
		@Override
		public final void run() {
			// DQH - A bit ugly - want to use Executor-s but don't really want to rely on their 
			// queue-ing mechanism, so each worker handles a small block -- then shuts down to 
			// avoid monopolizing the Executor which could be shared.
			// However, at the end, it spins up  a replacement just to make sure the number of 
			// background workers remains relatively constant.
			
			for ( int i = 0; i < 4; ++i ) {
				InputProvider<? extends I> provider = ParallelAggregatingIterator.this.providerQueue.poll();
				if ( provider == null ) {
					return;
				}
				
				ParallelAggregatingIterator.this.run(provider);
			}
			
			if ( ! ParallelAggregatingIterator.this.isDone() ) {
				ParallelAggregatingIterator.this.scheduleWorker();
			}
		}
	}
	
	private static final class End {}
}
