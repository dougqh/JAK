package net.dougqh.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public final class Accumulator<T> {
	public static interface Task<T> {
		public abstract void run(final Accumulator<T> accumulator) throws Exception;
	}
	
	private static final End END = new End();
	
	private final ConcurrentLinkedQueue<Task<T>> taskQueue;
	private final AtomicInteger activeTasks = new AtomicInteger(0);
	
	private final ConcurrentLinkedQueue<T> resultQueue;
	private volatile Throwable cause = null;
	
	public Accumulator() {
		this.taskQueue = new ConcurrentLinkedQueue<Task<T>>();
		this.resultQueue = new ConcurrentLinkedQueue<T>();
	}
	
	public final void schedule(final Task<T> task) {
		this.taskQueue.add(task);
	}
	
	public final void exception(final Throwable cause) {
		if ( this.cause == null ) {
			this.cause = cause;
		}
	}
	
	public final void result(final T value) throws InterruptedException {
		this.resultQueue.add(value);
	}
	
	public final Iterator<T> iterator() {
		return new IteratorImpl();
	}
	
	protected final void runTask(final Task<T> task) {
		this.activeTasks.incrementAndGet();
		try {
			task.run(this);
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
		Task<T> task = this.taskQueue.poll();
		if ( task != null ) {
			this.runTask(task);
		}
	}
	
	private final boolean isDone() {
		if ( this.cause != null ) {
			return true;
		}
		if ( ! this.taskQueue.isEmpty() ) {
			return false;
		}
		if ( ! this.resultQueue.isEmpty() ) {
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
		
		T value = this.resultQueue.poll();
		if ( value != null ) {
			return value;
		}
		
		while ( this.resultQueue.isEmpty() ) {
			this.runTaskNow();
			
			this.checkForExceptions();
			
			if ( this.isDone() ) {
				return END;
			}
		}
		
		return this.resultQueue.poll();
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
}
