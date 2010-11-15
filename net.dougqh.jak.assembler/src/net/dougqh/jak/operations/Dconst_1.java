package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;

public final class Dconst_1 extends Operation {
	public static final String ID = "dconst_1";
	public static final byte CODE = DCONST_1;
	
	public static final Dconst_1 instance() {
		return new Dconst_1();
	}
	
	private Dconst_1() {}
	
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
		writer.dconst_1();
	}
}