package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;

public final class Fload_0 extends Operation {
	public static final String ID = "fload_0";
	public static final byte CODE = FLOAD_0;
	
	public static final Fload_0 instance() {
		return new Fload_0();
	}
	
	private Fload_0() {}
	
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
		return new Class< ? >[] { float.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.fload_0();
	}
}