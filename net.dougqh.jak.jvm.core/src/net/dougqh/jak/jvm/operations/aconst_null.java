package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Reference;

public final class aconst_null extends ConstantOperation {
	public static final String ID = "aconst_null";
	public static final byte CODE = ACONST_NULL;
	
	public static final aconst_null instance() {
		return new aconst_null();
	}
	
	private aconst_null() {}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final Type codeInputType() {
		return null;
	}
	
	@Override
	public final Type type() {
		return Reference.class;
	}
	
	@Override
	public final <T> T value() {
		return null;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.aconst_null();
	}
}