package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;

public final class L2i extends Operation {
	public static final String ID = "l2i";
	public static final byte CODE = L2I;
	
	public static final L2i instance() {
		return new L2i();
	}
	
	private L2i() {}
	
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
		return new Class< ? >[] { int.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.l2i();
	}
}