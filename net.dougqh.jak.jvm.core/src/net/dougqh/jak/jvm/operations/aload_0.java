package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Reference;

public final class aload_0 extends FixedLoadOperation {
	public static final String ID = "aload_0";
	public static final byte CODE = ALOAD_0;
	
	public static final aload_0 instance() {
		return new aload_0();
	}
	
	private aload_0() {}
	
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
		processor.aload_0();
	}
}