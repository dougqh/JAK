package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Any;

public final class arraylength extends BaseJvmOperation {
	public static final String ID = "arraylength";
	public static final byte CODE = ARRAYLENGTH;
	
	public static final arraylength instance() {
		return new arraylength();
	}
	
	private arraylength() {}
	
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
	public final String getOperator() {
		return null;
	}
	
	@Override
	public final Type[] getCodeOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Type[] getStackOperandTypes() {
		return new Type[] { Any[].class };
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		return new Type[] { int.class };
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.arraylength();
	}
}