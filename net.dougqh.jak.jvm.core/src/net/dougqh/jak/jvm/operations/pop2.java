package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Category2;

public final class pop2 extends StackManipulationOperation {
	public static final String ID = "pop2";
	public static final byte CODE = POP2;
	
	public static final pop2 instance() {
		return new pop2();
	}
	
	private pop2() {}
	
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
	public final Type[] getStackOperandTypes() {
		return new Type[] { Category2.class };
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		return NO_RESULTS;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.pop2();
	}
}