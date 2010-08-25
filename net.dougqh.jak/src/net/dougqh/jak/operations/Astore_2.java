package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.types.Reference;

public final class Astore_2 extends Operation {
	public static final String ID = "astore_2";
	public static final byte CODE = ASTORE_2;
	
	public static final Astore_2 instance() {
		return new Astore_2();
	}
	
	private Astore_2() {}
	
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
		return NO_RESULTS;	
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.astore_2();
	}
}