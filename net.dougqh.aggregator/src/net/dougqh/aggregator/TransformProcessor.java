package net.dougqh.aggregator;

public final class TransformProcessor<I, T, O> extends Processor<I, O> {
	private final Processor<I, T> processor;
	private final Transform<T, O> transform;
	
	public TransformProcessor(
		final Processor<I, T> processor,
		final Transform<T, O> transform)
	{
		this.processor = processor;
		this.transform = transform;
	}
	
	@Override
	public final void process(final InputChannel<I> in, final OutputChannel<O> out)
		throws Exception
	{
		TransformingOutputChannel<T, O> transformedChannel = new TransformingOutputChannel<T, O>(this.transform, out);
		try {
			processor.process(in, transformedChannel);
		} finally {
			// Propagate any captured transform failures
			
			// In this case, transform failures should trump exceptions from running process, 
			// so finally has the desired semantics.
			transformedChannel.checkForException();
		}
	}
	
	static final class TransformingOutputChannel<I, T> implements OutputChannel<I> {
		private final Transform<I, T> transform;
		private final OutputChannel<T> out;
		
		private Throwable cause = null;
		
		public TransformingOutputChannel(
			final Transform<I, T> transform,
			final OutputChannel<T> out)
		{
			this.transform = transform;
			this.out = out;
		}
		
		@Override
		public final void offer(final I offer) {
			try {
				this.out.offer(this.transform.transform(offer));
			} catch ( Exception e ) {
				this.cause = e;
			}
		}
		
		public final void checkForException() throws Exception {
			if ( this.cause == null ) {
				return;
			} else if ( this.cause instanceof RuntimeException ) {
				throw (RuntimeException)this.cause;
			} else if ( this.cause instanceof Error ) {
				throw (Error)this.cause;
			} else if ( this.cause instanceof Exception ) {
				throw (Exception)this.cause;
			} else {
				throw new IllegalStateException(this.cause);
			}
		}
	}
}
