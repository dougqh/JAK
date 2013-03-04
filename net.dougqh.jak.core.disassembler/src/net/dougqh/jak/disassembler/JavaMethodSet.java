package net.dougqh.jak.disassembler;

import java.util.ArrayList;
import java.util.List;

import net.dougqh.functional.Filter;

public abstract class JavaMethodSet<T extends JavaMethod> implements Iterable<T>{
	public abstract boolean isEmpty();
	
	public abstract int size();
	
	public abstract T get(final int index);
	
	public abstract JavaMethodSet<T> filter(final Filter<? super T> filter);
	
	// Called by implementations of filter, so the implementation can be mostly
	// final but allow for returning a sub-class if necessary.
	protected final List<T> filterHelper(final Filter<? super T> filter) {
		ArrayList<T> matches = new ArrayList<T>( this.size() / 2 );
		
		for ( T method: this ) {
			if ( filter.matches(method) ) {
				matches.add(method);
			}
		}
		
		return matches;
	}
	
	public final T get(final Filter<? super T> filter) {
		for ( T method: this ) {
			if ( filter.matches(method) ) {
				return method;
			}
		}
		return null;
	}
	
	public final T get(final String name) {
		return this.get(new Filter<T>() {
			@Override
			public final boolean matches(final T method) {
				return method.getName().equals(name);
			}
		});
	}
}
