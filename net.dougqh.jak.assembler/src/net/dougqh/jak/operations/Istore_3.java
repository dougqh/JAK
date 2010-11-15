package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;

public final class Istore_3 extends Operation {
	public static final String ID = "istore_3";
	public static final byte CODE = ISTORE_3;
	
	public static final Istore_3 instance() {
		return new Istore_3();
	}
	
	private Istore_3() {}
	
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
		writer.istore_3();
	}
}