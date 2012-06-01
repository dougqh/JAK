package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor.Jump;

public abstract class IfOperation implements JvmOperation {
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
		return new Type[] { Jump.class };
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		return NO_RESULTS;
	}
}
