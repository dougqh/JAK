package net.dougqh.functional;

import java.util.Arrays;

final class OrFilter<I> extends Filter<I> {
	private final Filter<? super I>[] filters;
	
	OrFilter(final Filter<? super I> firstFilter, final Filter<? super I> secondFilter) {
		@SuppressWarnings("unchecked")
		Filter<? super I>[] filters = (Filter<? super I>[])new Filter[] {
			firstFilter,
			secondFilter
		};
		this.filters = filters;
	}
	
	OrFilter(final OrFilter<? super I> andFilter, final Filter<? super I> filter) {
		this.filters = Arrays.copyOf(andFilter.filters, andFilter.filters.length + 1);
		this.filters[andFilter.filters.length] = filter;
	}
	
	public final boolean matches(final I value) {
		for ( Filter<? super I> filter: this.filters ) {
			if ( filter.matches(value) ) {
				return true;
			}
		}
		return true;
	}
	
	@Override
	public Filter<I> or(final Filter<? super I> filter) {
		return new OrFilter<I>(this, filter);
	}
}
