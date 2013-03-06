package net.dougqh.collections;

import java.util.ListIterator;

import net.dougqh.functional.Transform;

public final class TransformedListIterator<I, O> implements ListIterator<O> {
	private final ListIterator<? extends I> listIterator;
	private final Transform<? super I, ? extends O> transform;
	
	public TransformedListIterator(
		final ListIterator<? extends I> listIterator,
		final Transform<? super I, ? extends O> transform)
	{
		this.listIterator = listIterator;
		this.transform = transform;
	}
	
	private final O transform(final I input) {
		try {
			return this.transform.transform(input);
		} catch ( Exception e ) {
			throw new IllegalStateException(e);
		}
	}
	
	@Override
	public final boolean hasNext() {
		return this.listIterator.hasNext();
	}
	
	@Override
	public final O next() {
		return this.transform(this.listIterator.next());
	}
	
	@Override
	public final int nextIndex() {
		return this.listIterator.nextIndex();
	}
	
	@Override
	public final boolean hasPrevious() {
		return this.listIterator.hasPrevious();
	}
	
	@Override
	public final O previous() {
		return this.transform(this.listIterator.previous());
	}
	
	@Override
	public final int previousIndex() {
		return this.listIterator.previousIndex();
	}
	
	@Override
	public final void add(final O e) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final void remove() {
		throw new UnsupportedOperationException();
	}
	
	public final void set(final O e) {
		throw new UnsupportedOperationException();
	}
}
