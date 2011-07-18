package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;
import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter.Jump;
import net.dougqh.jak.types.Reference;

public final class if_acmpne extends JvmOperation {
	public static final String ID = "if_acmpne";
	public static final byte CODE = IF_ACMPNE;
	
	public static final if_acmpne prototype() {
		return new if_acmpne( new JumpPrototype() );
	}
	
	private final Jump jump;
	
	public if_acmpne( final Jump jump ) {
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
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.if_acmpne( this.jump );
	}
}