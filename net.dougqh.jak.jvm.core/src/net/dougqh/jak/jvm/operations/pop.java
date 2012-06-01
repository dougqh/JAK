package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Category1;

public final class pop extends StackManipulationOperation {
	public static final String ID = "pop";
	public static final byte CODE = POP;
	
	public static final pop instance() {
		return new pop();
	}
	
	private pop() {}
	
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
		return new Type[] { Category1.class };
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.pop();
	}
}