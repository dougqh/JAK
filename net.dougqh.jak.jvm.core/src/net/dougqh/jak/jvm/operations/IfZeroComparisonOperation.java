package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

public abstract class IfZeroComparisonOperation extends IfOperation {
	public abstract Type type();
	
	@Override
	public final Type[] getStackOperandTypes() {
		return new Type[] { this.type() };
	}
}
