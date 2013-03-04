package net.dougqh.aggregator;

import java.util.Arrays;

final class AndFilter<I> extends Filter<I> {
	private final Filter<? super I>[] filters;
	
	AndFilter(final Filter<? super I> firstFilter, final Filter<? super I> secondFilter) {
		@SuppressWarnings("unchecked")
		Filter<? super I>[] filters = (Filter<? super I>[])new Filter[] {
			firstFilter,
			secondFilter
		};
		this.filters = filters;
	}
	
	AndFilter(final AndFilter<? super I> andFilter, final Filter<? super I> filter) {
		this.filters = Arrays.copyOf(andFilter.filters, andFilter.filters.length + 1);
		this.filters[andFilter.filters.length] = filter;
	}
	
	public final boolean matches(final I value) {
		for ( Filter<? super I> filter: this.filters ) {
			if ( ! filter.matches(value) ) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public Filter<I> and(final Filter<? super I> filter) {
		return new AndFilter<I>(this, filter);
	}
}
