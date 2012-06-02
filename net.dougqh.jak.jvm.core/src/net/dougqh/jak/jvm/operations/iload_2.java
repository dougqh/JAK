package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class iload_2 extends FixedLoadOperation {
	public static final String ID = "iload_2";
	public static final byte CODE = ILOAD_2;
	
	public static final iload_2 instance() {
		return new iload_2();
	}
	
	private iload_2() {}
	
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
		return int.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.iload_2();
	}
}