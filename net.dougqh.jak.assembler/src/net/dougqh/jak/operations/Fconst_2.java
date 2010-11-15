package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;

public final class Fconst_2 extends Operation {
	public static final String ID = "fconst_2";
	public static final byte CODE = FCONST_2;
	
	public static final Fconst_2 instance() {
		return new Fconst_2();
	}
	
	private Fconst_2() {}
	
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
		return new Class< ? >[] { float.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.fconst_2();
	}
}