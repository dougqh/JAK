package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

public abstract class CastOperation extends BaseJvmOperation {
	public abstract Type fromType();
	
	public abstract Type toType();
	
	@Override
	public final boolean isPolymorphic() {
		return false;
	}
	
	@Override
	public final String getOperator() {
		return null;
	}
	
	@Override
	public Type[] getCodeOperandTypes() {
		//DQH - not final because it is overridden by checkcast
		return NO_ARGS;
	}
	
	@Override
	public final Type[] getStackOperandTypes() {
		return new Type[] { this.fromType() };
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		return new Type[] { this.toType() };
	}
}
