package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.Operation;
import net.dougqh.jak.types.Category1;

public final class Swap extends Operation {
	public static final String ID = "swap";
	public static final byte CODE = SWAP;
	
	public static final Swap instance() {
		return new Swap();
	}
	
	private Swap() {}
	
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
		return new Class< ? >[] { Category1.class, Category1.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { Category1.class, Category1.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.swap();
	}
}