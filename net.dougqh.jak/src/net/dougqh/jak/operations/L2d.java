package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;

public final class L2d extends Operation {
	public static final String ID = "l2d";
	public static final byte CODE = L2D;
	
	public static final L2d instance() {
		return new L2d();
	}
	
	private L2d() {}
	
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
		return new Class< ? >[] { long.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { double.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.l2d();
	}
}