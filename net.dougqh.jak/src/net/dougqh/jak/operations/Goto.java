package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.JavaCoreCodeWriter.Jump;
import net.dougqh.jak.Operation;

public final class Goto extends Operation {
	public static final String ID = "goto";
	public static final byte CODE = GOTO;
	
	public static final Goto prototype() {
		return new Goto( new JumpPrototype() );
	}
	
	private final Jump jump;
	
	public Goto( final Jump jump ) {
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
		return NO_ARGS;
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return NO_RESULTS;
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.goto_( this.jump );
	}
}