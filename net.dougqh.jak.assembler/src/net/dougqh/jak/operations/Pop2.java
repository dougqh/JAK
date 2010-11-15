package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;
import net.dougqh.jak.types.Category2;

public final class Pop2 extends Operation {
	public static final String ID = "pop2";
	public static final byte CODE = POP2;
	
	public static final Pop2 instance() {
		return new Pop2();
	}
	
	private Pop2() {}
	
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
		return new Class< ? >[] { Category2.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return NO_RESULTS;
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.pop2();
	}
}