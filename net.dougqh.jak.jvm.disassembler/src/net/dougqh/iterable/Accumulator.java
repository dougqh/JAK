package net.dougqh.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public final class Accumulator<T> {
	public static interface Scheduler<T> {
		public abstract void schedule(final Task<T> task);
		
		public abstract void result(final T result);
		
		public abstract void exception(final Throwable cause);
	}
	
	public static interface Task<T> {
		public abstract void run(final Scheduler<T> scheduler) throws Exception;
	}
	
	private static final End END = new End();
	
	private final AtomicInteger activeTasks = new AtomicInteger(0);
	
	private final UnboundedScheduler unboundedScheduler = new UnboundedScheduler();
	private volatile Throwable cause = null;
	
	public final void initialize(final Task<T> task) {
		this.runTask(task);
	}
	
	public final Iterator<T> iterator() {
		return new IteratorImpl();
	}
	
	protected final void runTask(final Task<T> task) {
		this.activeTasks.incrementAndGet();
		try {
			task.run(this.unboundedScheduler);
		} catch ( Exception e ) {
			this.cause = e;
		} finally {
			this.activeTasks.decrementAndGet();
		}
	}
	
	protected final void checkForExceptions() {
		if ( this.cause != null ) {
			throw new IllegalStateException(this.cause);
		}
	}
	
	protected final void runTaskNow() {
		Task<T> task = this.unboundedScheduler.taskQueue.poll();
		if ( task != null ) {
			this.runTask(task);
		}
	}
	
	private final boolean isDone() {
		if ( this.cause != null ) {
			return true;
		}
		if ( ! this.unboundedScheduler.taskQueue.isEmpty() ) {
			return false;
		}
		if ( ! this.unboundedScheduler.resultQueue.isEmpty() ) {
			return false;
		}
		if ( this.activeTasks.get() != 0 ) {
			return false;
		}
		return true;
	}
	
	protected final Object poll() {
		// The accumulator is designed to work both as a multi-thread task queueing 
		// mechanism, but also as a single threaded on demand task pump.
		
		// Whenever there are results immediately available, the request thread 
		// takes it upon itself to run a task immediately to keep everything moving.
		// This immediate running in the current thread also allows the Accumulator
		// to work as a lazy evaluation mechanism even there is no background thread
		// pool performing any work.
		this.checkForExceptions();
		
		T value = this.unboundedScheduler.resultQueue.poll();
		if ( value != null ) {
			return value;
		}
		
		while ( this.unboundedScheduler.resultQueue.isEmpty() ) {
			this.runTaskNow();
			
			this.checkForExceptions();
			
			if ( this.isDone() ) {
				return END;
			}
		}
		
		return this.unboundedScheduler.resultQueue.poll();
	}
	
	private final class IteratorImpl<T> implements Iterator<T> {
		private Object value;
		
		IteratorImpl() {}
		
		private final void loadNext() {
			if ( this.value == null ) {
				this.value = Accumulator.this.poll();
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
	
	private final class UnboundedScheduler implements Scheduler<T> {
		private final ConcurrentLinkedQueue<Task<T>> taskQueue = new ConcurrentLinkedQueue<Task<T>>();
		private final ConcurrentLinkedQueue<T> resultQueue = new ConcurrentLinkedQueue<T>();
		
		@Override
		public final void schedule(final Task<T> task) {
			this.taskQueue.add(task);
		}
		
		public final void result(final T result) {
			this.resultQueue.add(result);
		}
		
		@Override
		public final void exception(final Throwable cause) {
			Accumulator.this.cause = cause;
		}
	}
}
