package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.types.Reference;

public final class Athrow extends Operation {
	public static final String ID = "athrow";
	public static final byte CODE = ATHROW;
	
	public static final Athrow instance() {
		return new Athrow();
	}
	
	private Athrow() {}
	
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
		return new Class< ? >[] { Reference.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.athrow();
	}
}