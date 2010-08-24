package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.Operation;

public final class I2s extends Operation {
	public static final String ID = "i2s";
	public static final byte CODE = I2S;
	
	public static final I2s instance() {
		return new I2s();
	}
	
	private I2s() {}
	
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
		return new Class< ? >[] { short.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.i2s();
	}
}