package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class athrow extends BaseJvmOperation {
	public static final String ID = "athrow";
	public static final byte CODE = ATHROW;
	
	public static final athrow instance() {
		return new athrow();
	}
	
	private athrow() {}
	
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
	public final String getOperator() {
		return null;
	}
	
	@Override
	public final Type[] getCodeOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Type[] getStackOperandTypes() {
		return new Type[] { Throwable.class };
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.athrow();
	}
}