package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.Operation;

public final class Iinc extends Operation {
	public static final String ID = "iinc";
	public static final byte CODE = IINC;
	
	public static final Iinc prototype() {
		return new Iinc( 0, 0 );
	}
	
	private final int slot;
	private final int amount;
	
	public Iinc( final int slot, final int amount ) {
		this.slot = slot;
		this.amount = amount;
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
		return new Class< ? >[] { int.class, int.class };
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return NO_RESULTS;
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.iinc( this.slot, this.amount );
	}
}