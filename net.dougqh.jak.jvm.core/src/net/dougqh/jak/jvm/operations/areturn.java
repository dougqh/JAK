package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Reference;

public final class areturn extends ReturnOperation {
	public static final String ID = "areturn";
	public static final byte CODE = ARETURN;
	
	public static final areturn instance() {
		return new areturn();
	}
	
	private areturn() {}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final Type type() {
		return Reference.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.areturn();
	}
}