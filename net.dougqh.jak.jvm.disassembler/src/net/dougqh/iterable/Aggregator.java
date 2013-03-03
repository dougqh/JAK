package net.dougqh.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public final class Aggregator<I> {
	public static interface Scheduler<I> {
		public abstract void schedule(final Task<I> task) throws InterruptedException;
		
		public abstract void result(final I result) throws InterruptedException;
		
		public abstract void exception(final Throwable cause);
	}
	
	public static interface Task<I> {
		public abstract void run(final Scheduler<I> scheduler) throws Exception;
	}
	
	private static final End END = new End();
	
	private final AtomicInteger activeTasks = new AtomicInteger(0);
	
	private final UnboundedScheduler unboundedScheduler = new UnboundedScheduler();
	private final BoundedScheduler boundedScheduler = new BoundedScheduler();
	
	private volatile Throwable cause = null;
	
	public final void initialize(final Task<I> task) {
		// Since the accumulator is usually initialized in the reading thread, 
		// use the unbounded scheduler to make sure the reading thread does not
		// block itself.
		this.runTask(this.unboundedScheduler, task);
	}
	
	public final Iterator<I> iterator() {
		return new IteratorImpl();
	}
	
	protected final void runTask(final Scheduler<I> scheduler, final Task<I> task) {
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
		I value = this.pollResult();
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
	
	protected final void checkForExceptions() {
		if ( this.cause != null ) {
			throw new IllegalStateException(this.cause);
		}
	}
	
	protected final Task<I> pollTask() {
		this.checkForExceptions();
		
		Task<I> task;
		
		task = this.unboundedScheduler.pollTask();
		if ( task != null ) {
			return task;
		}
		
		task = this.boundedScheduler.pollTask();
		if ( task != null ) {
			return task;
		}
		
		return null;
	}
	
	protected final boolean hasResults() {
		return this.unboundedScheduler.hasResults() ||
			this.boundedScheduler.hasResults();
	}
	
	protected final I pollResult() {
		this.checkForExceptions();
		
		I result;
		
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
		Task<I> task = this.pollTask();
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
	
	private final class IteratorImpl implements Iterator<I> {
		private Object value;
		
		IteratorImpl() {}
		
		private final void loadNext() {
			if ( this.value == null ) {
				this.value = Aggregator.this.pollNext();
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
		public final I next() {
			this.loadNext();
			
			if ( this.value instanceof End ) {
				throw new NoSuchElementException();
			}
			
			@SuppressWarnings("unchecked")
			I castedValue = (I)this.value;
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
		
		abstract Task<I> pollTask();
		
		abstract boolean hasResults();
		
		abstract I pollResult();
		
		@Override
		public final void exception(final Throwable cause) {
			Aggregator.this.cause = cause;
		}
	}
	
	private final class UnboundedScheduler extends ConcreteScheduler {
		private final ConcurrentLinkedQueue<Task<I>> taskQueue = new ConcurrentLinkedQueue<Task<I>>();
		private final ConcurrentLinkedQueue<I> resultQueue = new ConcurrentLinkedQueue<I>();
		
		@Override
		final Task<I> pollTask() {
			return this.taskQueue.poll();
		}
		
		@Override
		final boolean hasResults() {
			return ! this.resultQueue.isEmpty();
		}
		
		@Override
		final I pollResult() {
			return this.resultQueue.poll();
		}
		
		@Override
		public final void schedule(final Task<I> task) {
			this.taskQueue.add(task);
		}
		
		public final void result(final I result) {
			this.resultQueue.add(result);
		}
		
		@Override
		public final boolean isCompletelyEmpty() {
			return this.taskQueue.isEmpty() && this.resultQueue.isEmpty();
		}
	}
	
	private final class BoundedScheduler extends ConcreteScheduler {
		private final LinkedBlockingQueue<Task<I>> taskQueue = new LinkedBlockingQueue<Aggregator.Task<I>>();
		private final LinkedBlockingQueue<I> resultQueue = new LinkedBlockingQueue<I>();
		
		@Override
		final Task<I> pollTask() {
			return this.taskQueue.poll();
		}
		
		@Override
		final boolean hasResults() {
			return ! this.resultQueue.isEmpty();
		}
		
		@Override
		final I pollResult() {
			return this.resultQueue.poll();
		}
		
		@Override
		public final void schedule(final Task<I> task) throws InterruptedException {
			this.taskQueue.put(task);
		}
		
		public final void result(final I result) throws InterruptedException {
			this.resultQueue.put(result);
		}
		
		@Override
		public final boolean isCompletelyEmpty() {
			return this.taskQueue.isEmpty() && this.resultQueue.isEmpty();
		}
	}
}