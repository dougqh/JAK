package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;

public final class fconst_1 extends JvmOperation {
	public static final String ID = "fconst_1";
	public static final byte CODE = FCONST_1;
	
	public static final fconst_1 instance() {
		return new fconst_1();
	}
	
	private fconst_1() {}
	
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
		return new Class< ? >[] { float.class };
	}
	
	@Override
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.fconst_1();
	}
}