package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

public abstract class IfComparisonOperation extends IfOperation {
	public abstract Type lhsType();
	
	public abstract Type rhsType();
	
	@Override
	public final Type[] getStackOperandTypes() {
		return new Type[] { this.lhsType(), this.rhsType() };
	}
}
