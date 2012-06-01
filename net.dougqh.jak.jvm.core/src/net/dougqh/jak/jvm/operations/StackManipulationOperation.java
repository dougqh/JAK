package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

public abstract class StackManipulationOperation implements JvmOperation {
	@Override
	public final String getOperator() {
		return null;
	}
	
	@Override
	public final Type[] getCodeOperandTypes() {
		return NO_ARGS;
	}

}
