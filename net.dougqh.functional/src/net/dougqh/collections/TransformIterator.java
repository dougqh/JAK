package net.dougqh.collections;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;

public abstract class TransformIterator< I, O > implements Iterator< O > {
	private final Iterator< ? extends I > iterator;
	
	public TransformIterator( final Enumeration< ? extends I > enumeration ) {
		this( new EnumerationIterator< I >( enumeration ) );
	}

	public TransformIterator( final I... inputs ) {
		this( Arrays.asList( inputs ) );
	}
	
	public TransformIterator( final Iterable< ? extends I > iterable ) {
		this( iterable.iterator() );
	}
	
	public TransformIterator( final Iterator< ? extends I > iterator ) {
		this.iterator = iterator;
	}
	
	@Override
	public final boolean hasNext() {
		return this.iterator.hasNext();
	}
	
	@Override
	public final O next() {
		return this.transform( this.iterator.next() );
	}
	
	@Override
	public final void remove() {
		throw new UnsupportedOperationException();
	}
	
	protected abstract O transform( final I input );
}
