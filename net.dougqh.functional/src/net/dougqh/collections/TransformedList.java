package net.dougqh.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.dougqh.functional.Transform;

public class TransformedList<I, O> implements List<O> {
	private final List<? extends I> list;
	private final Transform<? super I, ? extends O> transform;
	
	public TransformedList(
		final List<? extends I> list,
		final Transform<? super I, ? extends O> transform)
	{
		this.list = list;
		this.transform = transform;
	}
	
	private final O transform(final I input) {
		try {
			return this.transform.transform(input);
		} catch ( Exception e ) {
			throw new IllegalStateException(e);
		}
	}
	
	public final void add(final int index, final O element) {
		throw new UnsupportedOperationException();
	}
	
	public final boolean add(final O element) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final boolean addAll(Collection<? extends O> elements) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final boolean addAll(final int index, final Collection<? extends O> collection) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final void clear() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final boolean contains(final Object needle) {
		for (O curElement: this) {
			if ( curElement.equals(needle) ) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		for ( Object needle: c ) {
			if ( ! this.contains(needle) ) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public final O get(final int index) {
		return this.transform(this.list.get(index));
	}
	
	@Override
	public final boolean isEmpty() {
		return this.list.isEmpty();
	}
	
	@Override
	public final int indexOf(final Object needle) {
		for ( ListIterator<O> iter = this.listIterator(); iter.hasNext(); ) {
			int index = iter.nextIndex();
			O curElement = iter.next();
			
			if ( curElement.equals(needle) ) {
				return index;
			}
		}
		return -1;
	}
	
	@Override
	public final int lastIndexOf(final Object needle) {
		for ( ListIterator<O> iter = this.listIterator(this.size()); iter.hasPrevious(); ) {
			int index = iter.previousIndex();
			O curElement = iter.previous();
			
			if ( curElement.equals(needle) ) {
				return index;
			}
		}
		return -1;
	}
	
	@Override
	public final ListIterator<O> listIterator() {
		return new TransformedListIterator<I, O>(this.list.listIterator(), this.transform);
	}
	
	@Override
	public ListIterator<O> listIterator(final int index) {
		return new TransformedListIterator<I, O>(this.list.listIterator(index), this.transform);
	}
	
	@Override
	public final Iterator<O> iterator() {
		return this.listIterator();
	}
	
	@Override
	public final O remove(final int index) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final boolean remove(final Object o) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final boolean retainAll(final Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	
	public final O set(final int index, final O element) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final List<O> subList(final int fromIndex, final int toIndex) {
		return new TransformedList<I, O>(this.list.subList(fromIndex, toIndex), this.transform);
	}
	
	@Override
	public final int size() {
		return this.list.size();
	}
	
	@Override
	public final Object[] toArray() {
		Object[] objects = new Object[this.size()];
		
		for ( ListIterator<O> iter = this.listIterator(); iter.hasNext(); ) {
			int index = iter.nextIndex();
			O curElement = iter.next();
			
			objects[index] = curElement;
		}
		
		return objects;
	}
	
	public <T extends Object> T[] toArray(T[] a) {
		// TODO: Implement later
		throw new UnsupportedOperationException();
	}
}
