package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class iload_1 extends FixedLoadOperation {
	public static final String ID = "iload_1";
	public static final byte CODE = ILOAD_1;
	
	public static final iload_1 instance() {
		return new iload_1();
	}
	
	private iload_1() {}
	
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
		return int.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.iload_1();
	}
}