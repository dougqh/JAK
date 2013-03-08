package net.dougqh.aggregator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

class ParallelAggregatingIterator<I, O> implements Iterator<O> {
	private static final End END = new End();
	
	private final InputProvider<? extends I> rootProvider;
	private final Processor<? super I, ? extends O> processor;
	
	private Object value;
	
	private final AtomicInteger activeTasks = new AtomicInteger(0);
	
	private final UnboundedScheduler unboundedScheduler = new UnboundedScheduler();
	private final BoundedScheduler boundedScheduler = new BoundedScheduler();
	
	private volatile Throwable cause = null;
	
	public ParallelAggregatingIterator(
		final InputProvider<? extends I> rootProvider,
		final Processor<? super I, ? extends O> processor)
	{
		this.rootProvider = rootProvider;
		this.processor = processor;
	}
	
	private final void loadNext() {
		if ( this.value == null ) {
			this.value = this.awaitNext();
		}
	}
	
	private final void clear() {
		this.value = null;
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
	
	
	protected final void runProvider(
		final InputScheduler<? super I> scheduler,
		final InputProvider<? extends I> task)
	{
		this.activeTasks.incrementAndGet();
		try {
			task.run(scheduler);
		} catch ( Exception e ) {
			this.cause = e;
		} finally {
			this.activeTasks.decrementAndGet();
		}
	}
	
	protected final Object awaitNext() {
		// The accumulator is designed to work both as a multi-thread task queueing 
		// mechanism, but also as a single threaded on demand task pump.
		
		// Whenever there are results immediately available, the request thread 
		// takes it upon itself to run a input provider immediately to keep 
		// everything moving.
		// This immediate running in the current thread also allows the 
		// AggregatingPipeline to work as a lazy evaluation mechanism even there
		// is no background thread pool performing any work.
		// Although, this is not its primary purpose and without a doubt a simpler 
		// implementation would be more efficient in terms of time and space.
		O value = this.pollResult();
		if ( value != null ) {
			return value;
		}
		
		while ( ! this.hasResults() ) {
			this.runProviderNow();
			
			this.checkForExceptions();
			
			if ( this.isDone() ) {
				return END;
			}
		}
		
		return this.pollResult();
	}
	
	protected final void exception(final Throwable cause) {
		this.cause = cause;
	}
	
	protected final void checkForExceptions() {
		if ( this.cause != null ) {
			throw new IllegalStateException(this.cause);
		}
	}
	
	protected final InputProvider<? extends I> pollInputProvider() {
		this.checkForExceptions();
		
		InputProvider<? extends I> inputProvider;
		
		inputProvider = this.unboundedScheduler.pollInputProvider();
		if ( inputProvider != null ) {
			return inputProvider;
		}
		
		inputProvider = this.boundedScheduler.pollInputProvider();
		if ( inputProvider != null ) {
			return inputProvider;
		}
		
		return null;
	}
	
	protected final boolean hasResults() {
		return this.unboundedScheduler.hasResults() ||
			this.boundedScheduler.hasResults();
	}
	
	protected final O pollResult() {
		this.checkForExceptions();
		
		O result;
		
		result = this.unboundedScheduler.pollResult();
		if ( result != null ) {
			return result;
		}
		
		result = this.boundedScheduler.pollResult();
		if ( result != null ) {
			return result;
		}
		
		return null;
	}
	
	protected final void runProviderNow() {
		InputProvider<? extends I> provider = this.pollInputProvider();
		if ( provider != null ) {
			this.runProvider(this.unboundedScheduler, provider);
		}
	}
	
	private final boolean isDone() {
		if ( this.cause != null ) {
			return true;
		}
		if ( ! this.unboundedScheduler.isCompletelyEmpty() ) {
			return false;
		}
		if ( ! this.boundedScheduler.isCompletelyEmpty() ) {
			return false;
		}
		if ( this.activeTasks.get() != 0 ) {
			return false;
		}
		return true;
	}
	
	
	private static final class End {}
	
	private abstract class ConcreteScheduler implements InputScheduler<I> {
		public final int numThreads() {
			// TODO: Implement with a return value that corresponds to the thread pool
			return 1;
		}
		
		abstract boolean isCompletelyEmpty();
		
		abstract InputProvider<? extends I> pollInputProvider();
		
		abstract boolean hasResults();
		
		abstract O pollResult();
	}
	
	final class BackgroundWorkerRunnable implements Runnable {
		@Override
		public final void run() {
			while ( ! ParallelAggregatingIterator.this.isDone() ) {
				InputProvider<? extends I> inputProvider = ParallelAggregatingIterator.this.pollInputProvider();
				try {
					inputProvider.run(ParallelAggregatingIterator.this.boundedScheduler);
				} catch (Throwable t) {
					ParallelAggregatingIterator.this.exception(t);
				}
			}
		}
	}
	
	static final class SingletonInputChannel<I> implements InputChannel<I> {
		private volatile I input = null;
		
		public final void put(final I input) {
			this.input = input;
		}
		
		public final I poll() {
			I input = this.input;
			this.input = null;
			return input;
		}		
	}
	
	private final class UnboundedScheduler extends ConcreteScheduler {
		private final ConcurrentLinkedQueue<InputProvider<? extends I>> providerQueue = 
			new ConcurrentLinkedQueue<InputProvider<? extends I>>();
		private final ConcurrentLinkedQueue<O> resultQueue = new ConcurrentLinkedQueue<O>();
		
		private final SingletonInputChannel<I> inputChannel = new SingletonInputChannel<I>();
		private final OutputChannel<O> outputChannel = new QueueBasedOutputChannel<O>(this.resultQueue);
		
		@Override
		final InputProvider<? extends I> pollInputProvider() {
			return this.providerQueue.poll();
		}
		
		@Override
		final boolean hasResults() {
			return ! this.resultQueue.isEmpty();
		}
		
		@Override
		final O pollResult() {
			return this.resultQueue.poll();
		}
		
		@Override
		public final void schedule(final InputProvider<? extends I> task) {
			this.providerQueue.add(task);
		}
		
		public final void offer(final I input) {
			this.inputChannel.put(input);
				
			try {
				ParallelAggregatingIterator.this.processor.process(this.inputChannel, this.outputChannel);
			} catch (Exception e) {
				ParallelAggregatingIterator.this.exception(e);
			}
		}
		
		@Override
		public final boolean isCompletelyEmpty() {
			return this.providerQueue.isEmpty() && this.resultQueue.isEmpty();
		}
	}
	
	private final class BoundedScheduler extends ConcreteScheduler {
		private final LinkedBlockingQueue<InputProvider<? extends I>> providerQueue = 
			new LinkedBlockingQueue<InputProvider<? extends I>>();
		private final LinkedBlockingQueue<O> resultQueue = new LinkedBlockingQueue<O>();
		
		private final SingletonInputChannel<I> inputChannel = new SingletonInputChannel<I>();
		private final OutputChannel<O> outputChannel = new QueueBasedOutputChannel<O>(this.resultQueue);
		
		@Override
		final InputProvider<? extends I> pollInputProvider() {
			return this.providerQueue.poll();
		}
		
		@Override
		final boolean hasResults() {
			return ! this.resultQueue.isEmpty();
		}
		
		@Override
		final O pollResult() {
			return this.resultQueue.poll();
		}
		
		@Override
		public final void schedule(final InputProvider<? extends I> provider) throws InterruptedException {
			this.providerQueue.put(provider);
		}
		
		public final void offer(final I input) throws InterruptedException {
			this.inputChannel.put(input);
			
			try {
				ParallelAggregatingIterator.this.processor.process(this.inputChannel, this.outputChannel);
			} catch (Exception e) {
				ParallelAggregatingIterator.this.exception(e);
			}
		}
		
		@Override
		public final boolean isCompletelyEmpty() {
			return this.providerQueue.isEmpty() && this.resultQueue.isEmpty();
		}
	}
}
