package net.dougqh.collections;

import java.util.Iterator;

public abstract class RecursiveIterable< B, L > implements Iterable< L > {
	private final B root;
	
	public RecursiveIterable( final B root ) {
		this.root = root;
	}
	
	@Override
	public final Iterator< L > iterator() {
		return new RecursiveIterator< B, L >( this.root ) {
			@Override
			protected final Iterator< ? extends B > branchIter( final B branch ) {
				return RecursiveIterable.this.branches( branch ).iterator();
			}
			
			@Override
			protected final Iterator< ? extends L > leafIter( final B branch ) {
				return RecursiveIterable.this.leaves( branch ).iterator();
			}
		};
	}

	protected abstract Iterable< ? extends B > branches( final B branch );
	
	protected abstract Iterable< ? extends L > leaves( final B branch );
}
