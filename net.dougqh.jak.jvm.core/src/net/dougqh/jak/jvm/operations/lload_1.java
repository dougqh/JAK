package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class lload_1 extends FixedLoadOperation {
	public static final String ID = "lload_1";
	public static final byte CODE = LLOAD_1;
	
	public static final lload_1 instance() {
		return new lload_1();
	}
	
	private lload_1() {}
	
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
		return long.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.lload_1();
	}
}