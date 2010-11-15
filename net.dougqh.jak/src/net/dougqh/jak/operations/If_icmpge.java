package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;
import net.dougqh.jak.assembler.JavaCoreCodeWriter.Jump;

public final class If_icmpge extends Operation {
	public static final String ID = "if_icmpge";
	public static final byte CODE = IF_ICMPGE;
	
	public static final If_icmpge prototype() {
		return new If_icmpge( new JumpPrototype() );
	}
	
	private final Jump jump;
	
	public If_icmpge( final Jump jump ) {
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
		return new Class< ? >[] { int.class, int.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return NO_RESULTS;
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.if_icmpge( this.jump );
	}
}