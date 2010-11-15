package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;

public final class Fload extends Operation {
	public static final String ID = "fload";
	public static final byte CODE = FLOAD;
	
	public static final Fload prototype() {
		return new Fload( 0 );
	}
	
	private final int slot;
	
	public Fload( final int slot ) {
		this.slot = slot;
	}
	
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
		return new Class< ? >[] { int.class };
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { float.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.fload( this.slot );
	}
}