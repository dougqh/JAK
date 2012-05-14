package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;

public final class sipush extends JvmOperation {
	public static final String ID = "sipush";
	public static final byte CODE = SIPUSH;
	
	public static final sipush prototype() {
		return new sipush( (short)0 );
	}
	
	private final short value;
	
	public sipush( final short value ) {
		this.value = value;
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
		return new Class< ? >[] { short.class };
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { int.class };
	}
	
	@Override
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.sipush( this.value );
	}
}