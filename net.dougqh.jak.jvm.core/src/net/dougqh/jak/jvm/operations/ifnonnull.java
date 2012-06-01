package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Jump;
import net.dougqh.jak.types.Reference;

public final class ifnonnull extends IfZeroComparisonOperation {
	public static final String ID = "ifnonnull";
	public static final byte CODE = IFNONNULL;
	
	public static final ifnonnull prototype() {
		return new ifnonnull( new JumpPrototype() );
	}
	
	private final Jump jump;
	
	public ifnonnull( final Jump jump ) {
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
		return Reference.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.ifnonnull( this.jump );
	}
}