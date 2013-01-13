package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Jump;

public final class ifeq extends IfZeroComparisonOperation {
	public static final String ID = "ifeq";
	public static final byte CODE = IFEQ;
	
	public static final ifeq prototype() {
		return new ifeq(new JumpPrototype());
	}
	
	public ifeq( final Jump jump ) {
		super(jump);
	}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final Type type() {
		return int.class;
	}
	
	@Override
	public final void process(final JvmOperationProcessor processor) {
		processor.ifeq(this.jump());
	}
}