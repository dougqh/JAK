package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

public abstract class ComparisonOperation extends BaseJvmOperation {
	public abstract Type lhsType();
	
	public abstract Type rhsType();
	
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
		return new Type[] { this.lhsType(), this.lhsType() };
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		return new Type[] { int.class };
	}
}
