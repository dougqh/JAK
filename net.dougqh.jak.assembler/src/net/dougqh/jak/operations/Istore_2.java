package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;

public final class Istore_2 extends Operation {
	public static final String ID = "istore_2";
	public static final byte CODE = ISTORE_2;
	
	public static final Istore_2 instance() {
		return new Istore_2();
	}
	
	private Istore_2() {}
	
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
		return NO_RESULTS;
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.istore_2();
	}
}