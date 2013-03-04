package net.dougqh.aggregator;

public abstract class Filter<I> {
	public abstract boolean matches(final I value);
	
	public Filter<I> and(final Filter<? super I> filter) {
		return new AndFilter<I>(this, filter);
	}
	
	public Filter<I> or(final Filter<? super I> filter) {
		return new OrFilter<I>(this, filter);
	}
	
	public Filter<I> not() {
		return new NotFilter<I>(this);
	}
}
