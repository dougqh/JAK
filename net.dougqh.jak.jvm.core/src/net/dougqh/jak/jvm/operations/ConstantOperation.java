package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

public abstract class ConstantOperation implements JvmOperation {
	public Type codeInputType() {
		return null;
	}
	
	public abstract Type type();
	
	public abstract <T> T value();
	
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
		Type codeInputType = this.codeInputType();
		return codeInputType == null ? NO_ARGS : new Type[] { codeInputType };
	}
	
	@Override
	public final Type[] getStackOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		return new Type[] { this.type() };
	}
	
	protected static final <T> T as(final Object value) {
		@SuppressWarnings("unchecked")
		T castedValue = (T)value;
		return castedValue;
	}
}
