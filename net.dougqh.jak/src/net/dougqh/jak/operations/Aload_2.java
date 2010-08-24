package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.Operation;
import net.dougqh.jak.types.Reference;

public final class Aload_2 extends Operation {
	public static final String ID = "aload_2";
	public static final byte CODE = ALOAD_2;
	
	public static final Aload_2 instance() {
		return new Aload_2();
	}
	
	private Aload_2() {}
	
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
		writer.aload_2();
	}
}