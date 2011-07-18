package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;

public final class castore extends JvmOperation {
	public static final String ID = "castore";
	public static final byte CODE = CASTORE;
	
	public static final castore instance() {
		return new castore();
	}
	
	private castore() {}
	
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
		return new Class< ? >[] { char[].class, int.class, char.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.castore();
	}
}