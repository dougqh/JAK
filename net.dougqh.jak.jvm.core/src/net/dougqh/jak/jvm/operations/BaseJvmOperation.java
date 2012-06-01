package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.JvmOperationProcessor;


public abstract class BaseJvmOperation implements JvmOperation {
	protected static final Class< ? >[] NO_ARGS = {};
	protected static final Class< ? >[] NO_RESULTS = {};
	
	protected BaseJvmOperation() {}
	
	@Override
	public abstract String getId();
	
	@Override
	public abstract int getCode();
	
	@Override
	public String getOperator() {
		return null;
	}
	
	@Override
	public boolean isPolymorphic() {
		return false;
	}
	
	@Override
	public abstract Class< ? >[] getCodeOperandTypes();
	
	@Override
	public abstract Class< ? >[] getStackOperandTypes();
	
	@Override
	public abstract Class< ? >[] getStackResultTypes();
	
	@Override
	public abstract void process( final JvmOperationProcessor coreWriter );
}
