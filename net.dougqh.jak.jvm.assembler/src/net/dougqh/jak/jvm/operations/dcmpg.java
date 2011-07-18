package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;

public final class dcmpg extends JvmOperation {
	public static final String ID = "dcmpg";
	public static final byte CODE = DCMPG;
	
	public static final dcmpg instance() {
		return new dcmpg();
	}
	
	private dcmpg() {}
	
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
		return new Class< ? >[] { double.class, double.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { int.class };
	}
	
	@Override
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.dcmpg();
	}
}