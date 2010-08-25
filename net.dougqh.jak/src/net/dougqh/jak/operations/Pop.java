package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.types.Category1;

public final class Pop extends Operation {
	public static final String ID = "pop";
	public static final byte CODE = POP;
	
	public static final Pop instance() {
		return new Pop();
	}
	
	private Pop() {}
	
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
		return new Class< ? >[] { Category1.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.pop();
	}
}