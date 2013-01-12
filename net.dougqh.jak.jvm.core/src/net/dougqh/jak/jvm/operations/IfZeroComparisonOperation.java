package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor.Jump;

public abstract class IfZeroComparisonOperation extends IfOperation {
	public IfZeroComparisonOperation( final Jump jump ) {
		super(jump);
	}
	
	public abstract Type type();
	
	@Override
	public final Type[] getStackOperandTypes() {
		return new Type[] { this.type() };
	}
}
