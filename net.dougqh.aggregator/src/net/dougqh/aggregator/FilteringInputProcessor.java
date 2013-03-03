package net.dougqh.aggregator;


final class FilteringInputProcessor<I, O> extends InputProcessor<I, O> {
	private final InputProcessor<I, O> processor;
	private final Filter<I> filter;
	
	public FilteringInputProcessor(final Filter<I> filter, final InputProcessor<I, O> processor) {
		this.processor = processor;
		this.filter = filter;
	}
	
	@Override
	public final void process(final InputChannel<I> in, final OutputChannel<O> out)
		throws Exception
	{
		this.processor.process(new FilteringInputChannel<I>(in, this.filter), out);
	}
	
	private static final class FilteringInputChannel<I> implements InputChannel<I> {
		private final InputChannel<I> in;
		private final Filter<I> filter;
		
		public FilteringInputChannel(final InputChannel<I> in, final Filter<I> filter) {
			this.in = in;
			this.filter = filter;
		}
		
		@Override
		public final I poll() {
			for ( I input = this.in.poll(); in != null; input = this.in.poll() ) {
				if ( this.filter.matches(input) ) {
					return input;
				}
			}
			return null;
		}
	}
}
