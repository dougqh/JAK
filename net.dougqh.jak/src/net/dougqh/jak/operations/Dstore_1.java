package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;

public final class Dstore_1 extends Operation {
	public static final String ID = "dstore_1";
	public static final byte CODE = DSTORE_1;
	
	public static final Dstore_1 instance() {
		return new Dstore_1();
	}
	
	private Dstore_1() {}
	
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
		return new Class< ? >[] { double.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return NO_RESULTS;
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.dstore_1();
	}
}