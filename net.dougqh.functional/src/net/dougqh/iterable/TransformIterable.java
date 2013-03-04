package net.dougqh.iterable;

import java.util.Arrays;
import java.util.Iterator;

public abstract class TransformIterable< I, O > implements Iterable< O > {
	private final Iterable< ? extends I > iterable;
	
	public TransformIterable( final I... inputs ) {
		this.iterable = Arrays.asList( inputs );
	}
	
	public TransformIterable( final Iterable< ? extends I > iterable ) {
		this.iterable = iterable;
	}
	
	@Override
	public final Iterator< O > iterator() {
		return new TransformIterator< I, O >( this.iterable.iterator() ) {
			@Override
			protected final O transform( final I input ) {
				return TransformIterable.this.transform( input );
			}
		};
	}
	
	protected abstract O transform( final I input );
}
