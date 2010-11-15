package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;
import net.dougqh.jak.types.Reference;

public final class Astore_3 extends Operation {
	public static final String ID = "astore_3";
	public static final byte CODE = ASTORE_3;
	
	public static final Astore_3 instance() {
		return new Astore_3();
	}
	
	private Astore_3() {}
	
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
		writer.astore_3();
	}
}