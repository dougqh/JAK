package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;
import net.dougqh.jak.types.Category2;

public final class dup2 extends JvmOperation {
	public static final String ID = "dup2";
	public static final byte CODE = DUP2;
	
	public static final dup2 instance() {
		return new dup2();
	}
	
	private dup2() {}
	
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
		return new Class< ? >[] { Category2.class, Category2.class };
	}
	
	@Override
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.dup2();
	}
}