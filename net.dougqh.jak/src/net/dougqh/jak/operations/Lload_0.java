package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.Operation;

public final class Lload_0 extends Operation {
	public static final String ID = "lload_0";
	public static final byte CODE = LLOAD_0;
	
	public static final Lload_0 instance() {
		return new Lload_0();
	}
	
	private Lload_0() {}
	
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
		return NO_ARGS;
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { long.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.lload_0();
	}
}