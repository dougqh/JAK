package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

public abstract class UnaryOperation implements JvmOperation {
	public abstract Type inputType();
	
	public abstract Type resultType();
	
	public abstract <T> T fold( final Object value );
	
	@Override
	public final boolean isPolymorphic() {
		return false;
	}
	
	@Override
	public final Type[] getCodeOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Type[] getStackOperandTypes() {
		return new Type[] { this.inputType() };
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		return new Type[] { this.resultType() };
	}
	
	protected static final <T> T as(final Object value) {
		@SuppressWarnings("unchecked")
		T castedValue = (T)value;
		return castedValue;
	}
}
