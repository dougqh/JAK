package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;

public final class I2c extends Operation {
	public static final String ID = "i2c";
	public static final byte CODE = I2C;
	
	public static final I2c instance() {
		return new I2c();
	}
	
	private I2c() {}
	
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
		return new Class< ? >[] { char.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.i2c();
	}
}