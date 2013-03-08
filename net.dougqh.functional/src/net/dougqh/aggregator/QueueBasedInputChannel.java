package net.dougqh.aggregator;

import java.util.Queue;

final class QueueBasedInputChannel<I> implements InputChannel<I> {
	private final Queue<I> queue;
	
	QueueBasedInputChannel(final Queue<I> queue) {
		this.queue = queue;
	}
	
	@Override
	public final I poll() {
		return this.queue.poll();
	}
}
