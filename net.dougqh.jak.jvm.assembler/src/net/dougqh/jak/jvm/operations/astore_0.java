package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;
import net.dougqh.jak.types.Reference;

public final class astore_0 extends JvmOperation {
	public static final String ID = "astore_0";
	public static final byte CODE = ASTORE_0;
	
	public static final astore_0 instance() {
		return new astore_0();
	}
	
	private astore_0() {}
	
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
		return NO_ARGS;
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		return new Class< ? >[] { Reference.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return NO_RESULTS;
		
	}
	
	@Override
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.astore_0();
	}
}