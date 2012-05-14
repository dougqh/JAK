package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;

public final class fstore_2 extends JvmOperation {
	public static final String ID = "fstore_2";
	public static final byte CODE = FSTORE_2;
	
	public static final fstore_2 instance() {
		return new fstore_2();
	}
	
	private fstore_2() {}
	
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
		return new Class< ? >[] { float.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return NO_RESULTS;
	}
	
	@Override
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.fstore_2();
	}
}