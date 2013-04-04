package net.dougqh.collections;

import java.util.Iterator;

public final class CompositeIterator< E > implements Iterator< E > {
	private final Iterator< ? extends Iterator< ? extends E > > iteratorIter;
	private Iterator< ? extends E > iter = new EmptyIterator< E >();
	
	public CompositeIterator(
		final Iterator< ? extends Iterator< ? extends E > > iteratorIter )
	{
		this.iteratorIter = iteratorIter;
		this.prime();
	}
	
	@Override
	public final boolean hasNext() {
		return this.iter.hasNext();
	}
	
	@Override
	public final E next() {
		return this.iter.next();
	}
	
	@Override
	public final void remove() {
		throw new UnsupportedOperationException();
	}
	
	private final void prime() {
		while ( ! this.iter.hasNext() && this.iteratorIter.hasNext() ) {
			this.iter = this.iteratorIter.next();
		}
	}
}
