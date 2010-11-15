package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;
import net.dougqh.jak.types.Reference;

public final class Astore_0 extends Operation {
	public static final String ID = "astore_0";
	public static final byte CODE = ASTORE_0;
	
	public static final Astore_0 instance() {
		return new Astore_0();
	}
	
	private Astore_0() {}
	
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
		writer.astore_0();
	}
}