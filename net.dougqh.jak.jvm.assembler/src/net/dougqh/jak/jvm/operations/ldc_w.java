package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.ConstantEntry;
import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;
import net.dougqh.jak.types.Category1;

public final class ldc_w extends JvmOperation {
	public static final String ID = "ldc_w";
	public static final byte CODE = LDC_W;
	
	public static final ldc_w prototype() {
		return new ldc_w( 0 );
	}
	
	private final int constantPoolRef;
	
	public ldc_w( final int constantPoolRef ) {
		this.constantPoolRef = constantPoolRef;
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
		return new Class< ? >[] { int.class };
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { Category1.class };
	}
	
	@Override
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.ldc_w( ConstantEntry.category1( this.constantPoolRef ) );
	}
}