package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Jump;

public final class ifgt extends IfZeroComparisonOperation {
	public static final String ID = "ifgt";
	public static final byte CODE = IFGT;
	
	public static final ifgt prototype() {
		return new ifgt(new JumpPrototype());
	}
	
	public ifgt( final Jump jump ) {
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
	public final void process( final JvmOperationProcessor processor ) {
		processor.ifgt(this.jump());
	}
}