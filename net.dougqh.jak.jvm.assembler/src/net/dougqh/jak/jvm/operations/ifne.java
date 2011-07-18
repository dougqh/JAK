package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;
import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter.Jump;

public final class ifne extends JvmOperation {
	public static final String ID = "ifne";
	public static final byte CODE = IFNE;
	
	public static final ifne prototype() {
		return new ifne( new JumpPrototype() );
	}
	
	private final Jump jump;
	
	public ifne( final Jump jump ) {
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
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.ifne( this.jump );
	}
}