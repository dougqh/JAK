package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class iinc implements JvmOperation {
	public static final String ID = "iinc";
	public static final byte CODE = IINC;
	
	public static final iinc prototype() {
		return new iinc( 0, 0 );
	}
	
	private final int slot;
	private final int amount;
	
	public iinc( final int slot, final int amount ) {
		this.slot = slot;
		this.amount = amount;
	}
	
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
		return new Type[] { int.class, int.class };
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
		processor.iinc( this.slot, this.amount );
	}
}