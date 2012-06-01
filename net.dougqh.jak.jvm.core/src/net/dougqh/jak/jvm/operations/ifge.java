package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Jump;

public final class ifge extends IfZeroComparisonOperation {
	public static final String ID = "ifge";
	public static final byte CODE = IFGE;
	
	public static final ifge prototype() {
		return new ifge( new JumpPrototype() );
	}
	
	private final Jump jump;
	
	public ifge( final Jump jump ) {
		this.jump = jump;
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
		processor.ifge( this.jump );
	}
}