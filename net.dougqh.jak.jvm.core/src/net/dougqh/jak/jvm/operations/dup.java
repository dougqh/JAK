package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Category1;

public final class dup extends StackManipulationOperation {
	public static final String ID = "dup";
	public static final byte CODE = DUP;
	
	public static final dup instance() {
		return new dup();
	}
	
	private dup() {}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final boolean isPolymorphic() {
		return false;
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		return new Class< ? >[] { Category1.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { Category1.class, Category1.class };
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.dup();
	}
}