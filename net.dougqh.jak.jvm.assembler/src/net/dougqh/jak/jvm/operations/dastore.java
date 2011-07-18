package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;

public final class dastore extends JvmOperation {
	public static final String ID = "dastore";
	public static final byte CODE = DASTORE;
	
	public static final dastore instance() {
		return new dastore();
	}
	
	private dastore() {}
	
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
		return new Class< ? >[] { double[].class, int.class, double.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.dastore();
	}
}