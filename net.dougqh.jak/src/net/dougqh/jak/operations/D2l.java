package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.Operation;

public final class D2l extends Operation {
	public static final String ID = "d2l";
	public static final byte CODE = D2L;
	
	public static final D2l instance() {
		return new D2l();
	}
	
	private D2l() {}
	
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
		return new Class< ? >[] { double.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { long.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.d2l();
	}
}