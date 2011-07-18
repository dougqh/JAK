package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;

public final class baload extends JvmOperation {
	public static final String ID = "baload";
	public static final byte CODE = BALOAD;
	
	public static final baload instance() {
		return new baload();
	}
	
	private baload() {}
	
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
		return new Class< ? >[] { byte[].class, int.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { byte.class };
	}
	
	@Override
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.baload();
	}
}