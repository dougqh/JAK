package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Jump;
import net.dougqh.jak.types.Reference;

public final class ifnull extends IfZeroComparisonOperation {
	public static final String ID = "ifnull";
	public static final byte CODE = IFNULL;
	
	public static final ifnull prototype() {
		return new ifnull(new JumpPrototype());
	}
	
	public ifnull(final Jump jump) {
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
		return Reference.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.ifnull(this.jump());
	}
}