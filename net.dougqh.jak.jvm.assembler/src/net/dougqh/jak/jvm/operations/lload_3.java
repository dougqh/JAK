package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;

public final class lload_3 extends JvmOperation {
	public static final String ID = "lload_3";
	public static final byte CODE = LLOAD_3;
	
	public static final lload_3 instance() {
		return new lload_3();
	}
	
	private lload_3() {}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final Class<?>[] getCodeOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { long.class };
	}
	
	@Override
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.lload_3();
	}
}