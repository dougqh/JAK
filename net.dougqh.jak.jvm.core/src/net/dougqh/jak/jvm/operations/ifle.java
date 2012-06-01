package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Jump;

public final class ifle extends IfZeroComparisonOperation {
	public static final String ID = "ifle";
	public static final byte CODE = IFLE;
	
	public static final ifle prototype() {
		return new ifle( new JumpPrototype() );
	}
	
	private final Jump jump;
	
	public ifle( final Jump jump ) {
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
		processor.ifle( this.jump );
	}
}