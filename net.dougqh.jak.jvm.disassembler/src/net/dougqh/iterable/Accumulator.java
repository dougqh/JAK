package net.dougqh.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public final class Accumulator<T> {
	public static interface Scheduler<T> {
		public abstract void schedule(final Task<T> task) throws InterruptedException;
		
		public abstract void result(final T result) throws InterruptedException;
		
		public abstract void exception(final Throwable cause);
	}
	
	public static interface Task<T> {
		public abstract void run(final Scheduler<T> scheduler) throws Exception;
	}
	
	private static final End END = new End();
	
	private final AtomicInteger activeTasks = new AtomicInteger(0);
	
	private final UnboundedScheduler unboundedScheduler = new UnboundedScheduler();
	private final BoundedScheduler boundedScheduler = new BoundedScheduler();
	
	private volatile Throwable cause = null;
	
	public final void initialize(final Task<T> task) {
		// Since the accumulator is usually initialized in the reading thread, 
		// use the unbounded scheduler to make sure the reading thread does not
		// block itself.
		this.runTask(this.unboundedScheduler, task);
	}
	
	public final Iterator<T> iterator() {
		return new IteratorImpl();
	}
	
	protected final void runTask(final Scheduler<T> scheduler, final Task<T> task) {
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
		
		T value = this.pollResult();
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
	
	protected final Task<T> pollTask() {
		this.checkForExceptions();
		
		Task<T> task;
		
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
	
	protected final T pollResult() {
		this.checkForExceptions();
		
		T result;
		
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
		Task<T> task = this.pollTask();
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
	
	private final class IteratorImpl implements Iterator<T> {
		private Object value;
		
		IteratorImpl() {}
		
		private final void loadNext() {
			if ( this.value == null ) {
				this.value = Accumulator.this.pollNext();
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
		public final T next() {
			this.loadNext();
			
			if ( this.value instanceof End ) {
				throw new NoSuchElementException();
			}
			
			@SuppressWarnings("unchecked")
			T castedValue = (T)this.value;
			this.clear();
			return castedValue;
		}
		
		@Override
		public final void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	private static final class End {}
	
	private abstract class ConcreteScheduler implements Scheduler<T> {
		abstract boolean isCompletelyEmpty();
		
		abstract Task<T> pollTask();
		
		abstract boolean hasResults();
		
		abstract T pollResult();
		
		@Override
		public final void exception(final Throwable cause) {
			Accumulator.this.cause = cause;
		}
	}
	
	private final class UnboundedScheduler extends ConcreteScheduler {
		private final ConcurrentLinkedQueue<Task<T>> taskQueue = new ConcurrentLinkedQueue<Task<T>>();
		private final ConcurrentLinkedQueue<T> resultQueue = new ConcurrentLinkedQueue<T>();
		
		@Override
		final Task<T> pollTask() {
			return this.taskQueue.poll();
		}
		
		@Override
		final boolean hasResults() {
			return ! this.resultQueue.isEmpty();
		}
		
		@Override
		final T pollResult() {
			return this.resultQueue.poll();
		}
		
		@Override
		public final void schedule(final Task<T> task) {
			this.taskQueue.add(task);
		}
		
		public final void result(final T result) {
			this.resultQueue.add(result);
		}
		
		@Override
		public final boolean isCompletelyEmpty() {
			return this.taskQueue.isEmpty() && this.resultQueue.isEmpty();
		}
	}
	
	private final class BoundedScheduler extends ConcreteScheduler {
		private final LinkedBlockingQueue<Task<T>> taskQueue = new LinkedBlockingQueue<Accumulator.Task<T>>();
		private final LinkedBlockingQueue<T> resultQueue = new LinkedBlockingQueue<T>();
		
		@Override
		final Task<T> pollTask() {
			return this.taskQueue.poll();
		}
		
		@Override
		final boolean hasResults() {
			return ! this.resultQueue.isEmpty();
		}
		
		@Override
		final T pollResult() {
			return this.resultQueue.poll();
		}
		
		@Override
		public final void schedule(final Task<T> task) throws InterruptedException {
			this.taskQueue.put(task);
		}
		
		public final void result(final T result) throws InterruptedException {
			this.resultQueue.put(result);
		}
		
		@Override
		public final boolean isCompletelyEmpty() {
			return this.taskQueue.isEmpty() && this.resultQueue.isEmpty();
		}
	}
}