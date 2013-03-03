package net.dougqh.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public final class AggregatingPipeline<I, O> {
	public static interface Scheduler<I> {
		public abstract void schedule(final InputProvider<I> task) throws InterruptedException;
		
		public abstract void offer(final I result) throws InterruptedException;
	}
	
	public static interface OutputChannel<O> {
		public abstract void offer(final O offer);
	}
	
	public static interface InputProvider<I> {
		public abstract void run(final Scheduler<I> scheduler) throws Exception;
	}
	
	public static interface InputProcessor<I, O> {
		public abstract void process(final I input, final OutputChannel<O> out) throws Exception;
	}
	
	private static final End END = new End();
	
	private final InputProcessor<I, O> processor;
	private final AtomicInteger activeTasks = new AtomicInteger(0);
	
	private final UnboundedScheduler unboundedScheduler = new UnboundedScheduler();
	private final BoundedScheduler boundedScheduler = new BoundedScheduler();
	
	private volatile Throwable cause = null;
	
	public AggregatingPipeline(final InputProcessor<I, O> processor) {
		this.processor = processor;
	}
	
	public final void initialize(final InputProvider<I> task) {
		// Since the accumulator is usually initialized in the reading thread, 
		// use the unbounded scheduler to make sure the reading thread does not
		// block itself.
		this.runTask(this.unboundedScheduler, task);
	}
	
	public final Iterator<O> iterator() {
		return new IteratorImpl();
	}
	
	protected final void runTask(final Scheduler<I> scheduler, final InputProvider<I> task) {
		this.activeTasks.incrementAndGet();
		try {
			task.run(scheduler);
		} catch ( Exception e ) {
			this.cause = e;
		} finally {
			this.activeTasks.decrementAndGet();
		}
	}
	
	protected final Object pollNext() {
		// The accumulator is designed to work both as a multi-thread task queueing 
		// mechanism, but also as a single threaded on demand task pump.
		
		// Whenever there are results immediately available, the request thread 
		// takes it upon itself to run a task immediately to keep everything moving.
		// This immediate running in the current thread also allows the Accumulator
		// to work as a lazy evaluation mechanism even there is no background thread
		// pool performing any work.
		O value = this.pollResult();
		if ( value != null ) {
			return value;
		}
		
		while ( ! this.hasResults() ) {
			this.runTaskNow();
			
			this.checkForExceptions();
			
			if ( this.isDone() ) {
				return END;
			}
		}
		
		return this.pollResult();
	}
	
	protected final void exception(final Throwable cause) {
		AggregatingPipeline.this.cause = cause;
	}
	
	protected final void checkForExceptions() {
		if ( this.cause != null ) {
			throw new IllegalStateException(this.cause);
		}
	}
	
	protected final InputProvider<I> pollInputProvider() {
		this.checkForExceptions();
		
		InputProvider<I> inputProvider;
		
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
	
	protected final void runTaskNow() {
		InputProvider<I> task = this.pollInputProvider();
		if ( task != null ) {
			this.runTask(this.unboundedScheduler, task);
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
	
	private final class IteratorImpl implements Iterator<O> {
		private Object value;
		
		IteratorImpl() {}
		
		private final void loadNext() {
			if ( this.value == null ) {
				this.value = AggregatingPipeline.this.pollNext();
			}
		}
		
		private final void clear() {
			this.value = null;
		}
		
		@Override
		public final boolean hasNext() {
			this.loadNext();
			
			if ( this.value instanceof End ) {
				return false;
			} else {
				return true;
			}
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
	}
	
	private static final class End {}
	
	private abstract class ConcreteScheduler implements Scheduler<I> {
		abstract boolean isCompletelyEmpty();
		
		abstract InputProvider<I> pollInputProvider();
		
		abstract boolean hasResults();
		
		abstract O pollResult();
	}
	
	private final class UnboundedScheduler extends ConcreteScheduler {
		private final ConcurrentLinkedQueue<InputProvider<I>> taskQueue = new ConcurrentLinkedQueue<InputProvider<I>>();
		private final ConcurrentLinkedQueue<O> resultQueue = new ConcurrentLinkedQueue<O>();
		
		private final OutputChannel<O> outputChannel = new OutputChannel<O>() {
			@Override
			public final void offer(final O output) {
				UnboundedScheduler.this.resultQueue.offer(output);
			}
		};
		
		@Override
		final InputProvider<I> pollInputProvider() {
			return this.taskQueue.poll();
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
		public final void schedule(final InputProvider<I> task) {
			this.taskQueue.add(task);
		}
		
		public final void offer(final I input) {
			try {
				AggregatingPipeline.this.processor.process(input, this.outputChannel);
			} catch (Exception e) {
				AggregatingPipeline.this.exception(e);
			}
		}
		
		@Override
		public final boolean isCompletelyEmpty() {
			return this.taskQueue.isEmpty() && this.resultQueue.isEmpty();
		}
	}
	
	private final class BoundedScheduler extends ConcreteScheduler {
		private final LinkedBlockingQueue<InputProvider<I>> taskQueue = new LinkedBlockingQueue<AggregatingPipeline.InputProvider<I>>();
		private final LinkedBlockingQueue<O> resultQueue = new LinkedBlockingQueue<O>();
		
		private final OutputChannel<O> outputChannel = new OutputChannel<O>() {
			@Override
			public final void offer(final O output) {
				BoundedScheduler.this.resultQueue.offer(output);
			}
		};
		
		@Override
		final InputProvider<I> pollInputProvider() {
			return this.taskQueue.poll();
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
		public final void schedule(final InputProvider<I> task) throws InterruptedException {
			this.taskQueue.put(task);
		}
		
		public final void offer(final I input) throws InterruptedException {
			try {
				AggregatingPipeline.this.processor.process(input, this.outputChannel);
			} catch (Exception e) {
				AggregatingPipeline.this.exception(e);
			}
		}
		
		@Override
		public final boolean isCompletelyEmpty() {
			return this.taskQueue.isEmpty() && this.resultQueue.isEmpty();
		}
	}
}