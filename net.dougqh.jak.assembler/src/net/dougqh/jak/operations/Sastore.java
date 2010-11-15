package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;

public final class Sastore extends Operation {
	public static final String ID = "sastore";
	public static final byte CODE = SASTORE;
	
	public static final Sastore instance() {
		return new Sastore();
	}
	
	private Sastore() {}
	
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
		return new Class< ? >[] { short[].class, int.class, short.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return NO_RESULTS;
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.sastore();
	}
}