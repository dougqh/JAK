package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Reference;

public final class astore_1 extends FixedStoreOperation {
	public static final String ID = "astore_1";
	public static final byte CODE = ASTORE_1;
	
	public static final astore_1 instance() {
		return new astore_1();
	}
	
	private astore_1() {}
	
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
		return 1;
	}
	
	@Override
	public final Type type() {
		return Reference.class;
	}

	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.astore_1();
	}
}