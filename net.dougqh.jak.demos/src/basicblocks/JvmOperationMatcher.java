package basicblocks;

import net.dougqh.jak.jvm.operations.JvmOperation;

public abstract class JvmOperationMatcher<T extends JvmOperation> {
	private final Class<T> opClass;
	
  	public JvmOperationMatcher(final Class<T> opClass) {
		this.opClass = opClass;
	}
	
	public final boolean matches(final Class<? extends JvmOperation> opClass) {
		return this.opClass.isAssignableFrom(opClass);
	}
	
	public final boolean matches(final JvmOperation op) {
		if ( ! this.matches(op.getClass()) ) {
			return false;
		}
		
		@SuppressWarnings("unchecked")
		T castedOp = (T)op;
		return this.matchesCasted(castedOp);
	}
	
	public boolean matchesCasted(final T op) {
		return true;
	}
}
