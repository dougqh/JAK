package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;
import net.dougqh.jak.types.Category1;

public final class Dup_x1 extends Operation {
	public static final String ID = "dup_x1";
	public static final byte CODE = DUP_X1;
	
	public static final Dup_x1 instance() {
		return new Dup_x1();
	}
	
	private Dup_x1() {}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final boolean isPolymorphic() {
		return true;
	}
	
	@Override
	public final Class< ? >[] getCodeOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		return new Class< ? >[] { Category1.class, Category1.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { Category1.class, Category1.class, Category1.class };		
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.dup_x1();
	}
}