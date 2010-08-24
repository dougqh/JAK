package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.Operation;

public final class Castore extends Operation {
	public static final String ID = "castore";
	public static final byte CODE = CASTORE;
	
	public static final Castore instance() {
		return new Castore();
	}
	
	private Castore() {}
	
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
		return new Class< ? >[] { char[].class, int.class, char.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.castore();
	}
}