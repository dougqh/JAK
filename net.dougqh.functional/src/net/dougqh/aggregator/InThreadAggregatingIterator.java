package net.dougqh.aggregator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;

final class InThreadAggregatingIterator<I, O> implements Iterator<O> {
	private static final End END = new End();
	
	private final Processor<? super I, ? extends O> processor;
	
	private final ConcurrentLinkedQueue<InputProvider<? extends I>> providerQueue = 
		new ConcurrentLinkedQueue<InputProvider<? extends I>>();
	private final ConcurrentLinkedQueue<O> resultQueue = new ConcurrentLinkedQueue<O>();
	
	private final SchedulerImpl scheduler = new SchedulerImpl();
	
	private Object value = null;
	private Throwable cause = null;
	
	InThreadAggregatingIterator(
		final InputProvider<? extends I> rootProvider,
		final Processor<? super I, ? extends O> processor)
	{
		this.processor = processor;
		
		this.providerQueue.offer(rootProvider);
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
		while ( this.resultQueue.isEmpty() ) {
			InputProvider<? extends I> provider = this.providerQueue.poll();
			if ( provider == null ) {
				return END;
			}

			this.run(provider);
		}
		
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
	
	final void run(final InputProvider<? extends I> provider) {
		try {
			provider.run(this.scheduler);
			this.scheduler.flush();
		} catch ( Exception e ) {
			this.exception(e);
			
			this.checkForException();
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
			InThreadAggregatingIterator.this.providerQueue.offer(provider);
		}
		
		public final void offer(final I result) throws InterruptedException {
			this.inputQueue.offer(result);
		}
		
		public final void flush() throws Exception {
			InThreadAggregatingIterator.this.processor.process(
				new QueueBasedInputChannel<I>(this.inputQueue),
				new QueueBasedOutputChannel<O>(InThreadAggregatingIterator.this.resultQueue)
			);
		}
	}
	
	private static final class End {}
}
