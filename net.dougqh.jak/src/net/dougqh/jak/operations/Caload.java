package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.Operation;

public final class Caload extends Operation {
	public static final String ID = "caload";
	public static final byte CODE = CALOAD;
	
	public static final Caload instance() {
		return new Caload();
	}
	
	private Caload() {}
	
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
		return new Class< ? >[] { char[].class, int.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { char.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.caload();
	}
}