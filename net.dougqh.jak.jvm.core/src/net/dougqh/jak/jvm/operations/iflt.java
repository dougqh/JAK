package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Jump;

public final class iflt extends IfZeroComparisonOperation {
	public static final String ID = "iflt";
	public static final byte CODE = IFLT;
	
	public static final iflt prototype() {
		return new iflt( new JumpPrototype() );
	}
	
	private final Jump jump;
	
	public iflt( final Jump jump ) {
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
		processor.iflt( this.jump );
	}
}