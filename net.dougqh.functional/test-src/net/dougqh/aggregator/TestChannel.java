package net.dougqh.aggregator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

final class TestChannel<T> implements InputChannel<T>, OutputChannel<T>, Iterable<T> {
	private final LinkedList<T> list = new LinkedList<T>();
	
	public TestChannel(final T... values) {
		this.list.addAll(Arrays.asList(values));
	}
	
	@Override
	public final void offer(final T offer) {
		this.list.addLast(offer);
	}
	
	@Override
	public final T poll() {
		if ( this.list.isEmpty() ) {
			return null;
		} else {
			return this.list.removeFirst();
		}
	}
	
	public final List<T> list() {
		return this.list;
	}
	
	@Override
	public final Iterator<T> iterator() {
		return this.list.iterator();
	}
}
