package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Reference;

public final class aload_3 extends FixedLoadOperation {
	public static final String ID = "aload_3";
	public static final byte CODE = ALOAD_3;
	
	public static final aload_3 instance() {
		return new aload_3();
	}
	
	private aload_3() {}
	
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
		return 3;
	}
	
	@Override
	public final Type type() {
		return Reference.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.aload_3();
	}
}