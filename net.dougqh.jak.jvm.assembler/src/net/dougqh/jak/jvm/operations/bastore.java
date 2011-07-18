package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;

public final class bastore extends JvmOperation {
	public static final String ID = "bastore";
	public static final byte CODE = BASTORE;
	
	public static final bastore instance() {
		return new bastore();
	}
	
	private bastore() {}
	
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
		return new Class< ? >[] { byte[].class, int.class, byte.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.bastore();
	}
}