package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;
import net.dougqh.jak.types.Reference;

public final class astore_3 extends JvmOperation {
	public static final String ID = "astore_3";
	public static final byte CODE = ASTORE_3;
	
	public static final astore_3 instance() {
		return new astore_3();
	}
	
	private astore_3() {}
	
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
		writer.astore_3();
	}
}