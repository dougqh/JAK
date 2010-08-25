package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;

public final class Return extends Operation {
	public static final String ID = "return";
	public static final byte CODE = RETURN;
	
	public static final Return instance() {
		return new Return();
	}
	
	private Return() {}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final Class< ? >[] getCodeOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return NO_RESULTS;
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.return_();
	}
}