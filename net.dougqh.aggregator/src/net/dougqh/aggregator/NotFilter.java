package net.dougqh.aggregator;

final class NotFilter<I> extends Filter<I> {
	private final Filter<? super I> filter;
	
	public NotFilter(final Filter<? super I> filter) {
		this.filter = filter;
	}
	
	public boolean matches(I value) {
		return ! this.filter.matches(value);
	}
	
	@Override
	public final Filter<I> not() {
		@SuppressWarnings("unchecked")
		Filter<I> filter = (Filter<I>)this.filter;
		return filter;
	}
}
