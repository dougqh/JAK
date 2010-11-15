package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;
import net.dougqh.jak.types.Reference;

public final class Aconst_null extends Operation {
	public static final String ID = "aconst_null";
	public static final byte CODE = ACONST_NULL;
	
	public static final Aconst_null instance() {
		return new Aconst_null();
	}
	
	private Aconst_null() {}
	
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
		writer.aconst_null();
	}
}