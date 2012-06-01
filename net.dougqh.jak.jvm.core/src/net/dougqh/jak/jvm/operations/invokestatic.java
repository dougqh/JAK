package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Any;
import net.dougqh.jak.types.ArgList;

public final class invokestatic extends InvocationOperation {
	public static final String ID = "invokestatic";
	public static final byte CODE = INVOKESTATIC;
	
	public static final invokestatic prototype() {
		return new invokestatic( null, null );
	}
	
	private final Type targetType;
	private final JavaMethodDescriptor method;
	
	public invokestatic(
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
		processor.invokestatic( this.targetType, this.method );
	}
}