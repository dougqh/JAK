package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.Operation;

public final class Fadd extends Operation {
	public static final String ID = "fadd";
	public static final byte CODE = FADD;
	
	public static final Fadd instance() {
		return new Fadd();
	}
	
	private Fadd() {}
	
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
		writer.fadd();
	}
}