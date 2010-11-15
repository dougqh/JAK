package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;

public final class D2f extends Operation {
	public static final String ID = "d2f";
	public static final byte CODE = D2F;
	
	public static final D2f instance() {
		return new D2f();
	}
	
	private D2f() {}
	
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
		return new Class< ? >[] { double.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { float.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.d2f();
	}
}