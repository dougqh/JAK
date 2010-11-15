package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;
import net.dougqh.jak.assembler.JavaCoreCodeWriter.Jump;

public final class If_icmple extends Operation {
	public static final String ID = "if_icmple";
	public static final byte CODE = IF_ICMPLE;
	
	public static final If_icmple prototype() {
		return new If_icmple( new JumpPrototype() );
	}
	
	private final Jump jump;
	
	public If_icmple( final Jump jump ) {
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
		writer.if_icmple( this.jump );
	}
}