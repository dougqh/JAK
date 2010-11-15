package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;

public final class Baload extends Operation {
	public static final String ID = "baload";
	public static final byte CODE = BALOAD;
	
	public static final Baload instance() {
		return new Baload();
	}
	
	private Baload() {}
	
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
		return new Class< ? >[] { byte[].class, int.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { byte.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.baload();
	}
}