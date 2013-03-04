package net.dougqh.aggregator;


final class FilteringProcessor<I, O> extends Processor<I, O> {
	private final Processor<? super I, ? extends O> processor;
	private final Filter<? super O> filter;
	
	public FilteringProcessor(
		final Processor<? super I, ? extends O> processor,
		final Filter<? super O> filter)
	{
		this.processor = processor;
		this.filter = filter;
	}
	
	@Override
	public final void process(
		final InputChannel<? extends I> in,
		final OutputChannel<? super O> out)
		throws Exception
	{
		// Filtering processor can only shrink not grow the size of the input,
		// so just pass everything through immediately.
		
		this.processor.process(in, new FilteringOutputChannel<O>(this.filter, out));
	}
	
	@Override
	public final Processor<I, O> filter(final Filter<? super O> filter) {
		// Not sure why this cast is necessary - the compiler does not handle ? super ? super well
		@SuppressWarnings("unchecked")
		Filter<O> castedFilter = (Filter<O>)this.filter;
		
		return new FilteringProcessor<I, O>(this.processor, castedFilter.and(filter));
	}
	
	static final class FilteringOutputChannel<O> implements OutputChannel<O> {
		private final Filter<? super O> filter;
		private final OutputChannel<? super O> out;
		
		public FilteringOutputChannel(
			final Filter<? super O> filter,
			final OutputChannel<? super O> out)
		{
			this.filter = filter;
			this.out = out;
		}
		
		public final void offer(final O offer) {
			if ( this.filter.matches(offer) ) {
				this.out.offer(offer);
			}
		}
	}
}
