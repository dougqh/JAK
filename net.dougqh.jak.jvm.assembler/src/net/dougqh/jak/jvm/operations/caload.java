package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;

public final class caload extends JvmOperation {
	public static final String ID = "caload";
	public static final byte CODE = CALOAD;
	
	public static final caload instance() {
		return new caload();
	}
	
	private caload() {}
	
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
		return new Class< ? >[] { char[].class, int.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { char.class };
	}
	
	@Override
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.caload();
	}
}