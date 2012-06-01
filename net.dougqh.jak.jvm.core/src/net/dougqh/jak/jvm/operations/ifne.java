package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Jump;

public final class ifne extends IfZeroComparisonOperation {
	public static final String ID = "ifne";
	public static final byte CODE = IFNE;
	
	public static final ifne prototype() {
		return new ifne( new JumpPrototype() );
	}
	
	private final Jump jump;
	
	public ifne( final Jump jump ) {
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
		processor.ifne( this.jump );
	}
}