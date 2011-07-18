package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;
import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter.Jump;

public final class ifge extends JvmOperation {
	public static final String ID = "ifge";
	public static final byte CODE = IFGE;
	
	public static final ifge prototype() {
		return new ifge( new JumpPrototype() );
	}
	
	private final Jump jump;
	
	public ifge( final Jump jump ) {
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
		writer.ifge( this.jump );
	}
}