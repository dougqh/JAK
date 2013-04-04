package net.dougqh.aggregator;

import java.util.Queue;

final class QueueBasedOutputChannel<O> implements OutputChannel<O> {
	private final Queue<O> queue;
	
	QueueBasedOutputChannel(final Queue<O> queue) {
		this.queue = queue;
	}
	
	public final void offer(final O input) {
		this.queue.offer(input);
	}
}
