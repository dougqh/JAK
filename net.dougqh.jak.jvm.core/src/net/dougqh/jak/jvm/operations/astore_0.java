package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Reference;

public final class astore_0 extends StoreOperation {
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
	public final boolean isFixed() {
		return true;
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
		processor.astore_0();
	}
}