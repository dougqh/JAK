package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class iload_0 extends FixedLoadOperation {
	public static final String ID = "iload_0";
	public static final byte CODE = ILOAD_0;
	
	public static final iload_0 instance() {
		return new iload_0();
	}
	
	private iload_0() {}
	
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
		return int.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.iload_0();
	}
}