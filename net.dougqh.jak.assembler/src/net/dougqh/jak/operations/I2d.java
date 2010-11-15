package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;

public final class I2d extends Operation {
	public static final String ID = "i2d";
	public static final byte CODE = I2D;
	
	public static final I2d instance() {
		return new I2d();
	}
	
	private I2d() {}
	
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
		return new Class< ? >[] { double.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.i2d();
	}
}