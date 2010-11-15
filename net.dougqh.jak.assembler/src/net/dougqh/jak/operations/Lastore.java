package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;

public final class Lastore extends Operation {
	public static final String ID = "lastore";
	public static final byte CODE = LASTORE;
	
	public static final Lastore instance() {
		return new Lastore();
	}
	
	private Lastore() {}
	
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
		return new Class< ? >[] { long[].class, int.class, long.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return NO_RESULTS;
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.lastore();
	}
}