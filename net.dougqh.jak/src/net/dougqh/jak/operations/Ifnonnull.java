package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;
import net.dougqh.jak.assembler.JavaCoreCodeWriter.Jump;
import net.dougqh.jak.types.Reference;

public final class Ifnonnull extends Operation {
	public static final String ID = "ifnonnull";
	public static final byte CODE = IFNONNULL;
	
	public static final Ifnonnull prototype() {
		return new Ifnonnull( new JumpPrototype() );
	}
	
	private final Jump jump;
	
	public Ifnonnull( final Jump jump ) {
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
		return new Class< ? >[] { Reference.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.ifnonnull( this.jump );
	}
}