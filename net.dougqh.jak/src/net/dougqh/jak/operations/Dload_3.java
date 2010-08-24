package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.Operation;

public final class Dload_3 extends Operation {
	public static final String ID = "dload_3";
	public static final byte CODE = DLOAD_3;
	
	public static final Dload_3 instance() {
		return new Dload_3();
	}
	
	private Dload_3() {}
	
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
		return new Class< ? >[] { double.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.dload_3();
	}
}