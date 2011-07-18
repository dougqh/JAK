package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;

public final class i2c extends JvmOperation {
	public static final String ID = "i2c";
	public static final byte CODE = I2C;
	
	public static final i2c instance() {
		return new i2c();
	}
	
	private i2c() {}
	
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
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.i2c();
	}
}