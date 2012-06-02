package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class lload_2 extends FixedLoadOperation {
	public static final String ID = "lload_2";
	public static final byte CODE = LLOAD_2;
	
	public static final lload_2 instance() {
		return new lload_2();
	}
	
	private lload_2() {}
	
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
		return long.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.lload_2();
	}
}