package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.JvmOperationProcessor.Jump;

public abstract class BranchOperation extends BaseJvmOperation {
	private final Jump jump;
	
	public BranchOperation(final Jump jump) {
		this.jump = jump;
	}
	
	public abstract boolean isConditional();
	
	public final Jump jump() {
		return this.jump;
	}
}
