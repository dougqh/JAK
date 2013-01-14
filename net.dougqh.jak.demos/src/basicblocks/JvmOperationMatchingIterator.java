package basicblocks;

import java.util.Iterator;

import net.dougqh.jak.jvm.operations.JvmOperation;

public final class JvmOperationMatchingIterator implements Iterator<JvmOperation> {
	private final Iterator<? extends JvmOperation> opIter;
	private JvmOperation prior;
	private JvmOperation cur;
	
	public JvmOperationMatchingIterator(final Iterator<? extends JvmOperation> opIter) {
		this.opIter = opIter;
		
		this.prior = null;
		this.cur = null;
	}
	
	public final <T extends JvmOperation> T findNext(final Class<T> opClass) {
		while ( this.hasNext() ) {
			T next = this.nextIf(opClass);
			if ( next != null ) {
				return next;
			}
		}
		return null;
	}
	
	public final <T extends JvmOperation> T findNext(final JvmOperationMatcher<T> matcher) {
		while ( this.hasNext() ) {
			T next = this.nextIf(matcher);
			if ( next != null ) {
				return next;
			}
		}
		return null;
	}
	
	@Override
	public final boolean hasNext() {
		return this.opIter.hasNext();
	}
	
	@Override
	public final JvmOperation next() {
		this.prior = this.cur;
		
		this.cur = this.opIter.next();
		return this.cur;
	}
	
	public final <T extends JvmOperation> T nextIf(final Class<T> opClass) {
		JvmOperation op = this.next();
		if ( opClass.isAssignableFrom(op.getClass()) ) {
			@SuppressWarnings("unchecked")
			T castedOp = (T)op;
			return castedOp;
		} else {
			return null;
		}
	}
	
	public final <T extends JvmOperation> T nextIf(final JvmOperationMatcher<T> matcher) {
		JvmOperation op = this.next();
		if ( matcher.matches(op) ) {
			@SuppressWarnings("unchecked")
			T castedOp = (T)op;
			return castedOp;
		} else {
			return null;
		}
	}

	public final boolean hasPrior() {
		return ( this.prior != null );
	}
	
	public final JvmOperation prior() {
		return this.prior;
	}
	
	public final <T extends JvmOperation> T priorIf(final Class<T> opClass) {
		JvmOperation op = this.prior();
		if ( opClass.isAssignableFrom(op.getClass()) ) {
			@SuppressWarnings("unchecked")
			T castedOp = (T)op;
			return castedOp;
		} else {
			return null;
		}
	}
	
	public final <T extends JvmOperation> T priorIf(final JvmOperationMatcher<T> matcher) {
		JvmOperation op = this.prior();
		if ( matcher.matches(op) ) {
			@SuppressWarnings("unchecked")
			T castedOp = (T)op;
			return castedOp;
		} else {
			return null;
		}
	}
	
	@Override
	public final void remove() {
		throw new UnsupportedOperationException();
	}
}
