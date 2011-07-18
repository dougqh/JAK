package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;
import net.dougqh.jak.types.Reference;

public final class aconst_null extends JvmOperation {
	public static final String ID = "aconst_null";
	public static final byte CODE = ACONST_NULL;
	
	public static final aconst_null instance() {
		return new aconst_null();
	}
	
	private aconst_null() {}
	
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
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.aconst_null();
	}
}