package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;

public final class Saload extends Operation {
	public static final String ID = "saload";
	public static final byte CODE = SALOAD;
	
	public static final Saload instance() {
		return new Saload();
	}
	
	private Saload() {}
	
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
		return new Class< ? >[] { short[].class, int.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { short.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.saload();
	}
}