package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Reference;

public final class astore_3 extends FixedStoreOperation {
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
	public final int slot() {
		return 0;
	}
	
	@Override
	public final Type type() {
		return Reference.class;
	}

	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.astore_3();
	}
}