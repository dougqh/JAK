package net.dougqh.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class EmptyIterator< E > implements Iterator< E >  {
	public EmptyIterator() {}
	
	@Override
	public final boolean hasNext() {
		return false;
	}
	
	@Override
	public final E next() {
		throw new NoSuchElementException();
	}
	
	@Override
	public final void remove() {
		throw new UnsupportedOperationException();
	}
}
