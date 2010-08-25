package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;

public final class Ldiv extends Operation {
	public static final String ID = "ldiv";
	public static final byte CODE = LDIV;
	
	public static final Ldiv instance() {
		return new Ldiv();
	}
	
	private Ldiv() {}
	
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
		return new Class< ? >[] { long.class, long.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { long.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.ldiv();
	}
}