package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class invokespecial extends InvocationOperation {
	public static final String ID = "invokespecial";
	public static final byte CODE = INVOKESPECIAL;
	
	public static final invokespecial prototype() {
		return new invokespecial( null, null );
	}
	
	private final Type targetType;
	private final JavaMethodDescriptor method;

	public invokespecial(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.targetType = targetType;
		this.method = method;
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
	public final boolean isStatic() {
		return false;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.invokespecial( this.targetType, this.method );
	}
}