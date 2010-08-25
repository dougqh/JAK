package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;

public final class F2l extends Operation {
	public static final String ID = "f2l";
	public static final byte CODE = F2L;
	
	public static final F2l instance() {
		return new F2l();
	}
	
	private F2l() {}
	
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
		return new Class< ? >[] { float.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { long.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.f2l();
	}
}