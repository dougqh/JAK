package net.dougqh.iterable;

import java.util.Enumeration;
import java.util.Iterator;

public final class EnumerationIterator< E > implements Iterator< E > {
	private final Enumeration< ? extends E > enumeration;
	
	public EnumerationIterator( final Enumeration< ? extends E > enumeration ) {
		this.enumeration = enumeration;
	}
	
	@Override
	public final boolean hasNext() {
		return this.enumeration.hasMoreElements();
	}
	
	@Override
	public final E next() {
		return this.enumeration.nextElement();
	}
	
	@Override
	public final void remove() {
		throw new UnsupportedOperationException();
	}
}
