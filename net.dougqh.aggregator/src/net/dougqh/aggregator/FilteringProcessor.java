package net.dougqh.aggregator;


final class FilteringProcessor<I, O> extends Processor<I, O> {
	private final Processor<I, O> processor;
	private final Filter<O> filter;
	
	public FilteringProcessor(final Processor<I, O> processor, final Filter<O> filter) {
		this.processor = processor;
		this.filter = filter;
	}
	
	@Override
	public final void process(final InputChannel<I> in, final OutputChannel<O> out)
		throws Exception
	{
		// Filtering processor can only shrink not grow the size of the input,
		// so just pass everything through immediately.
		
		this.processor.process(in, new FilteringOutputChannel<O>(this.filter, out));
	}
	
	@Override
	public final Processor<I, O> filter(final Filter<O> filter) {
		return new FilteringProcessor<I, O>(this.processor, this.filter.and(filter));
	}
	
	static final class FilteringOutputChannel<O> implements OutputChannel<O> {
		private final Filter<O> filter;
		private final OutputChannel<O> out;
		
		public FilteringOutputChannel(final Filter<O> filter, final OutputChannel<O> out) {
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
