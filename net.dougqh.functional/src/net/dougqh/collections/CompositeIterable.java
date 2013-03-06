package net.dougqh.collections;

import java.util.Arrays;
import java.util.Iterator;

public final class CompositeIterable< E > implements Iterable< E > {
	private final Iterable< ? extends Iterable< ? extends E > > iterable;
	
	public CompositeIterable( final Iterable< ? extends E >... iterables ) {
		this( Arrays.asList( iterables ) );
	}
	
	public CompositeIterable( final Iterable< ? extends Iterable< ? extends E > > iterable ) {
		this.iterable = iterable;
	}
	
	@Override
	public final Iterator< E > iterator() {
		Iterator< Iterator< ? extends E > > iteratorIter = 
			new TransformIterator< Iterable< ? extends E >, Iterator< ? extends E > >( this.iterable ) {
				@Override
				protected final Iterator< ? extends E > transform( final Iterable< ? extends E > iterable ) {
					return iterable.iterator();
				}
			};
			
		return new CompositeIterator< E >( iteratorIter );
	}
}
