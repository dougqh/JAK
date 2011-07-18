package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;
import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter.Jump;
import net.dougqh.jak.types.Reference;

public final class ifnull extends JvmOperation {
	public static final String ID = "ifnull";
	public static final byte CODE = IFNULL;
	
	public static final ifnull prototype() {
		return new ifnull( new JumpPrototype() );
	}
	
	private final Jump jump;
	
	public ifnull( final Jump jump ) {
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
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.ifnull( this.jump );
	}
}