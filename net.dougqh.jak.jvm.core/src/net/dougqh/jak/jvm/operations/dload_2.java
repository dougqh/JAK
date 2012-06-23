package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class dload_2 extends FixedLoadOperation {
	public static final String ID = "dload_2";
	public static final byte CODE = DLOAD_2;
	
	public static final dload_2 instance() {
		return new dload_2();
	}
	
	private dload_2() {}
	
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
		return double.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.dload_2();
	}
}