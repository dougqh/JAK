package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.JavaCoreCodeWriter.Jump;
import net.dougqh.jak.Operation;

public final class Ifne extends Operation {
	public static final String ID = "ifne";
	public static final byte CODE = IFNE;
	
	public static final Ifne prototype() {
		return new Ifne( new JumpPrototype() );
	}
	
	private final Jump jump;
	
	public Ifne( final Jump jump ) {
		this.jump = jump;
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
		return new Class< ? >[] { Jump.class };
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		return new Class< ? >[] { int.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return NO_RESULTS;
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.ifne( this.jump );
	}
}