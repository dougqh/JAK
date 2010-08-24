package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.Operation;

public final class Drem extends Operation {
	public static final String ID = "drem";
	public static final byte CODE = DREM;
	
	public static final Drem instance() {
		return new Drem();
	}
	
	private Drem() {}
	
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
		return new Class< ? >[] { double.class, double.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { double.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.drem();
	}
}