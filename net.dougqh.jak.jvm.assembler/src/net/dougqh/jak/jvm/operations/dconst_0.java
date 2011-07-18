package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;

public final class dconst_0 extends JvmOperation {
	public static final String ID = "dconst_0";
	public static final byte CODE = DCONST_0;
	
	public static final dconst_0 instance() {
		return new dconst_0();
	}
	
	private dconst_0() {}
	
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
		return new Class< ? >[] { double.class };
	}
	
	@Override
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.dconst_0();
	}
}