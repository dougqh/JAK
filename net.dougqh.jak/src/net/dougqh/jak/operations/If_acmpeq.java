package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.JavaCoreCodeWriter.Jump;
import net.dougqh.jak.types.Reference;

public final class If_acmpeq extends Operation {
	public static final String ID = "if_acmpeq";
	public static final byte CODE = IF_ACMPEQ;
	
	public static final If_acmpeq prototype() {
		return new If_acmpeq( new JumpPrototype() );
	}
	
	private final Jump jump;
	
	public If_acmpeq( final Jump jump ) {
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
		return new Class< ? > [] { Reference.class, Reference.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return NO_RESULTS;
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.if_acmpeq( this.jump );
	}
}