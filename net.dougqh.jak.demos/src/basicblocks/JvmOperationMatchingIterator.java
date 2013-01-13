package basicblocks;

import java.util.Iterator;

import net.dougqh.jak.jvm.operations.JvmOperation;

public final class JvmOperationMatchingIterator implements Iterator<JvmOperation> {
	private final Iterator<? extends JvmOperation> opIter;
	
	public JvmOperationMatchingIterator(final Iterator<? extends JvmOperation> opIter) {
		this.opIter = opIter;
	}
	
	@Override
	public final boolean hasNext() {
		return this.opIter.hasNext();
	}
	
	@Override
	public final JvmOperation next() {
		return this.opIter.next();
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
	
	@Override
	public final void remove() {
		throw new UnsupportedOperationException();
	}
}
