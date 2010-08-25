package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;

public final class Lshr extends Operation {
	public static final String ID = "lshr";
	public static final byte CODE = LSHR;
	
	public static final Lshr instance() {
		return new Lshr();
	}
	
	private Lshr() {}
	
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
		return new Class< ? >[] { long.class, int.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { long.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.lshr();
	}
}