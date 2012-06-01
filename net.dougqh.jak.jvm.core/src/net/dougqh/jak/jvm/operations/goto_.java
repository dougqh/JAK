package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Jump;

public final class goto_ implements JvmOperation {
	public static final String ID = "goto";
	public static final byte CODE = GOTO;
	
	public static final goto_ prototype() {
		return new goto_( new JumpPrototype() );
	}
	
	private final Jump jump;
	
	public goto_( final Jump jump ) {
		this.jump = jump;
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
		return new Type[] { Jump.class };
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
		processor.goto_( this.jump );
	}
}