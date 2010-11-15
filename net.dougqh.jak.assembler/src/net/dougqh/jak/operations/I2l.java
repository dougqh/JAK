package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;

public final class I2l extends Operation {
	public static final String ID = "i2l";
	public static final byte CODE = I2L;
	
	public static final I2l instance() {
		return new I2l();
	}
	
	private I2l() {}
	
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
		return new Class< ? >[] { int.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { long.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.i2l();
	}
}