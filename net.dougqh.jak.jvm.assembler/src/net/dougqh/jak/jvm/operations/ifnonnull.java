package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;
import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter.Jump;
import net.dougqh.jak.types.Reference;

public final class ifnonnull extends JvmOperation {
	public static final String ID = "ifnonnull";
	public static final byte CODE = IFNONNULL;
	
	public static final ifnonnull prototype() {
		return new ifnonnull( new JumpPrototype() );
	}
	
	private final Jump jump;
	
	public ifnonnull( final Jump jump ) {
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
		writer.ifnonnull( this.jump );
	}
}