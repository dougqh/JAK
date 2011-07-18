package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;

public final class saload extends JvmOperation {
	public static final String ID = "saload";
	public static final byte CODE = SALOAD;
	
	public static final saload instance() {
		return new saload();
	}
	
	private saload() {}
	
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
		return new Class< ? >[] { short[].class, int.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { short.class };
	}
	
	@Override
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.saload();
	}
}