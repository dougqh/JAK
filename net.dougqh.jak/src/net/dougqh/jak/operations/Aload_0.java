package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.Operation;
import net.dougqh.jak.types.Reference;

public final class Aload_0 extends Operation {
	public static final String ID = "aload_0";
	public static final byte CODE = ALOAD_0;
	
	public static final Aload_0 instance() {
		return new Aload_0();
	}
	
	private Aload_0() {}
	
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
		return new Class< ? >[] { Reference.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.aload_0();
	}
}