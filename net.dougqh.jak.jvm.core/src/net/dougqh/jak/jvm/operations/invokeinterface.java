package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class invokeinterface extends InvocationOperation {
	public static final String ID = "invokeinterface";
	public static final byte CODE = INVOKEINTERFACE;
	
	public static final invokeinterface prototype() {
		return new invokeinterface( null, null );
	}
	
	public invokeinterface(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		super(targetType, method);
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
		processor.invokeinterface( this.targetType, this.method );
	}
}