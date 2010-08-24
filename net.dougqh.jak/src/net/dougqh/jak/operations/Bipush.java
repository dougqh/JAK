package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.Operation;

public final class Bipush extends Operation {
	public static final String ID = "bipush";
	public static final byte CODE = BIPUSH;
	
	public static final Bipush prototype() {
		return new Bipush( (byte)0 );
	}
	
	private final byte value;
	
	public  Bipush( final byte value ) {
		this.value = value;
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
		return new Class< ? >[] { byte.class };
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { byte.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.bipush( this.value );
	}
}