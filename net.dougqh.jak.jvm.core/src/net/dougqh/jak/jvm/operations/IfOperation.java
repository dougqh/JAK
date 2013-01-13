package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor.Jump;

public abstract class IfOperation extends BranchOperation {
	public IfOperation(final Jump jump) {
		super(jump);
	}
	
	@Override
	public final boolean isConditional() {
		return true;
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
	public final Type[] getStackResultTypes() {
		return NO_RESULTS;
	}
	
	@Override
	public String toString() {
		return this.getId() + " " + this.jump();
	}
}
