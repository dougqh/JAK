package net.dougqh.aggregator;

import net.dougqh.functional.Transform;

public final class TransformProcessor<I, T, O> extends Processor<I, O> {
	private final Processor<? super I, ? extends T> processor;
	private final Transform<? super T, ? extends O> transform;
	
	public TransformProcessor(
		final Processor<? super I, ? extends T> processor,
		final Transform<? super T, ? extends O> transform)
	{
		this.processor = processor;
		this.transform = transform;
	}
	
	@Override
	public final void process(final InputChannel<? extends I> in, final OutputChannel<? super O> out)
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
	
	@Override
	public <U> Processor<I, U> transform(final Transform<? super O, ? extends U> transform) {
		return new TransformProcessor<I, T, U>(
			this.processor,
			this.transform.chain(transform)
		);
	}
	
	static final class TransformingOutputChannel<I, T> implements OutputChannel<I> {
		private final Transform<? super I, ? extends T> transform;
		private final OutputChannel<? super T> out;
		
		private Throwable cause = null;
		
		public TransformingOutputChannel(
			final Transform<? super I, ? extends T> transform,
			final OutputChannel<? super T> out)
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
