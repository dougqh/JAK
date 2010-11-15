package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;
import net.dougqh.jak.types.Reference;

public final class Aload extends Operation {
	public static final String ID = "aload";
	public static final byte CODE = ALOAD;
	
	public static final Aload prototype() {
		return new Aload( 0 );
	}
	
	private final int slot;
	
	public Aload( final int slot ) {
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
		return new Class< ? >[] { Reference.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.aload( this.slot );
	}
}