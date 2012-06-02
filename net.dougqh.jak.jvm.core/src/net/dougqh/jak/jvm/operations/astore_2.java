package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Reference;

public final class astore_2 extends FixedStoreOperation {
	public static final String ID = "astore_2";
	public static final byte CODE = ASTORE_2;
	
	public static final astore_2 instance() {
		return new astore_2();
	}
	
	private astore_2() {}
	
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
		return 2;
	}
	
	@Override
	public final Type type() {
		return Reference.class;
	}

	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.astore_2();
	}
}