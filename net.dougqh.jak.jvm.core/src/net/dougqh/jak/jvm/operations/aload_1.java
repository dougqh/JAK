package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Reference;

public final class aload_1 extends LoadOperation {
	public static final String ID = "aload_1";
	public static final byte CODE = ALOAD_1;
	
	public static final aload_1 instance() {
		return new aload_1();
	}
	
	private aload_1() {}
	
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
		return 1;
	}
	
	@Override
	public final Type type() {
		return Reference.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.aload_1();
	}
}