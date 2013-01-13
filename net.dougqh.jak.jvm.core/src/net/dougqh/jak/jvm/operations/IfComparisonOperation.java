package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor.Jump;

public abstract class IfComparisonOperation extends IfOperation {
	public IfComparisonOperation(final Jump jump) {
		super(jump);
	}
	
	public abstract Type lhsType();
	
	public abstract Type rhsType();
	
	@Override
	public final Type[] getStackOperandTypes() {
		return new Type[] { this.lhsType(), this.rhsType() };
	}
}
