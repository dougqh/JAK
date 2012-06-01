package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Category1;

public final class swap extends StackManipulationOperation {
	public static final String ID = "swap";
	public static final byte CODE = SWAP;
	
	public static final swap instance() {
		return new swap();
	}
	
	private swap() {}
	
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
		return true;
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		return new Class< ? >[] { Category1.class, Category1.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { Category1.class, Category1.class };
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.swap();
	}
}