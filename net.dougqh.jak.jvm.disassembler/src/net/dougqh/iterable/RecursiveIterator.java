package net.dougqh.iterable;

import java.util.ArrayDeque;
import java.util.Iterator;

public abstract class RecursiveIterator< B, L > implements Iterator< L > {
	private final ArrayDeque< B > branches = new ArrayDeque< B >();
	private final ArrayDeque< L > leaves = new ArrayDeque< L >();
	
	public RecursiveIterator( final B root ) {
		this.branches.add( root );
		this.prime();
	}
	
	@Override
	public final boolean hasNext() {
		return ! this.leaves.isEmpty();
	}
	
	@Override
	public final L next() {
		L leaf = this.leaves.remove();
		this.prime();
		return leaf;
	}
	
	@Override
	public final void remove() {
		throw new UnsupportedOperationException();
	}
	
	protected abstract Iterator< ? extends B > branchIter( final B branch );
	
	protected abstract Iterator< ? extends L > leafIter( final B branch );
	
	private final void prime() {
		while ( this.leaves.isEmpty() && ! this.branches.isEmpty() ) {
			B branch = this.branches.remove();

			for ( Iterator< ? extends B > branchIter = this.branchIter( branch );
				branchIter.hasNext(); )
			{
				this.branches.add( branchIter.next() );
			}
			for ( Iterator< ? extends L > leafIter = this.leafIter( branch );
				leafIter.hasNext(); )
			{
				this.leaves.add( leafIter.next() );
			}
		}
	}
}
