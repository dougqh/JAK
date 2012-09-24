package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class nop implements JvmOperation {
	public static final String ID = "nop";
	public static final byte CODE = NOP;
	
	public static final nop instance() {
		return new nop();
	}
	
	private nop() {}
	
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
		return NO_ARGS;
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		return NO_RESULTS;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.nop();
	}
	
	
	@Override
	public final int hashCode() {
		return this.getCode();
	}
	
	@Override
	public final boolean equals(final Object obj) {
		return (obj instanceof nop);
	}
	
	@Override
	public final String toString() {
		return this.getId();
	}	
}