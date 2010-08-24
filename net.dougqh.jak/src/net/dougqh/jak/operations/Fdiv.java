package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.Operation;

public final class Fdiv extends Operation {
	public static final String ID = "fdiv";
	public static final byte CODE = FDIV;
	
	public static final Fdiv instance() {
		return new Fdiv();
	}
	
	private Fdiv() {}
	
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
		return new Class< ? >[] { float.class, float.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { float.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.fdiv();
	}
}