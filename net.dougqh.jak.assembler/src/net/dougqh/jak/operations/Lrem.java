package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;

public final class Lrem extends Operation {
	public static final String ID = "lrem";
	public static final byte CODE = LREM;
	
	public static final Lrem instance() {
		return new Lrem();
	}
	
	private Lrem() {}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final String getOperator() {
		return REM;
	}
	
	@Override
	public final Class< ? >[] getCodeOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		return new Class< ? >[] { long.class, long.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { long.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.lrem();
	}
}