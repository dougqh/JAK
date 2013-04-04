package net.dougqh.jist;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public final class Jist {
	private final Executor executor;
	private final int numWorkers;
	
	public Jist(final int numThreads) {
		this(createExecutor(numThreads), numThreads);
	}
	
	public Jist(final Executor executor, final int numWorkers) {
		this.executor = executor;
		this.numWorkers = numWorkers;
	}

	private static final ExecutorService createExecutor(final int numThreads) {
		final ThreadGroup threadGroup = new ThreadGroup("jist");
		
		return Executors.newFixedThreadPool(numThreads, new ThreadFactory() {
			@Override
			public final Thread newThread(final Runnable runnable) {
				return new Thread(threadGroup, runnable);
			}
		});
	}
	
	public final void shutdown() {
		if ( this.executor instanceof ExecutorService ) {
			ExecutorService executorService = (ExecutorService)this.executor;
			executorService.shutdown();
		}
	}
	
	public final void awaitTermination(final int timeout, final TimeUnit unit)
		throws InterruptedException
	{
		if ( this.executor instanceof ExecutorService ) {
			ExecutorService executorService = (ExecutorService)this.executor;
			executorService.awaitTermination(timeout, unit);
		}
	}
}
