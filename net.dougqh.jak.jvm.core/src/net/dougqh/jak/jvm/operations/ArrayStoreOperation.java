package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

public abstract class ArrayStoreOperation implements JvmOperation {
	public abstract Type arrayType();
	
	public abstract Type elementType();
	
	@Override
	public String getOperator() {
		return null;
	}
	
	@Override
	public boolean isPolymorphic() {
		return false;
	}
	
	@Override
	public final Type[] getCodeOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Type[] getStackOperandTypes() {
		return new Type[] { this.arrayType(), int.class, this.elementType() };
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		return NO_RESULTS;
	}
}
