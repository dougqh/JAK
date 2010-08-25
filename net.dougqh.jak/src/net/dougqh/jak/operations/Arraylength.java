package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.types.Any;

public final class Arraylength extends Operation {
	public static final String ID = "arraylength";
	public static final byte CODE = ARRAYLENGTH;
	
	public static final Arraylength instance() {
		return new Arraylength();
	}
	
	private Arraylength() {}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final boolean isPolymorphic() {
		return true;
	}
	
	@Override
	public final Class< ? >[] getCodeOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		return new Class< ? >[] { Any[].class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { int.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.arraylength();
	}
}