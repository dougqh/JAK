package net.dougqh.aggregator;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

final class ConditionallyBlockingQueue<T> {
	private final int capacity;
	
	private final ConcurrentLinkedQueue<T> queue = new ConcurrentLinkedQueue<T>();
	private volatile boolean limited = false;
	private final AtomicInteger count = new AtomicInteger(0);
	
	private final Lock lock = new ReentrantLock();
	private final Condition notFullCondition = lock.newCondition();
	
	public ConditionallyBlockingQueue() {
		this(32);
	}
	
	public ConditionallyBlockingQueue(final int capacity) {
		this.capacity = capacity;
	}
	
	public final void suspendLimit() {
		this.limited = false;
	}
	
	public final void restoreLimit() {
		this.limited = true;
	}
	
	public final boolean isEmpty() {
		return this.queue.isEmpty();
	}
	
	public final void offer(final T value) throws InterruptedException {
		// There is a deliberate choice here to allow capacity to be exceeded occasionally.
		// This is done so that an immediate thread used 
		
		while ( this.limited && this.count.get() >= this.capacity ) {
			this.await();
		}
		
		this.queue.offer(value);
	}
	
	private final void await() throws InterruptedException {
		this.lock.lock();
		try {
			this.notFullCondition.await();
		} finally {
			this.lock.unlock();
		}
	}
	
	public final T poll() {
		T result = this.queue.poll();
		if ( result != null ) {
			int newCount = this.count.decrementAndGet();
			if ( newCount < this.capacity ) {
				this.signal();
			}
		}
		
		return result;
	}
	
	private final void signal() {
		this.lock.lock();
		try {
			this.notFullCondition.signal();
		} finally {
			this.lock.unlock();
		}
	}
}
