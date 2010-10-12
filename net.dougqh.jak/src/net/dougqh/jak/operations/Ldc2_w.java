package net.dougqh.jak.operations;

import net.dougqh.jak.ConstantEntry;
import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.types.Category2;

public final class Ldc2_w extends Operation {
	public static final String ID = "ldc2_w";
	public static final byte CODE = LDC2_W;
	
	public static final Ldc2_w prototype() {
		return new Ldc2_w( 0 );
	}
	
	private final int constantPoolRef;
	
	public Ldc2_w( final int constantPoolRef ) {
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
		return new Class< ? >[] { Category2.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.ldc2_w( ConstantEntry.category2( this.constantPoolRef ) );
	}
}