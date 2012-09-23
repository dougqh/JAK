package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class invokevirtual extends InvocationOperation {
	public static final String ID = "invokevirtual";
	public static final byte CODE = INVOKEVIRTUAL;
	
	public static final invokevirtual prototype() {
		return new invokevirtual( null, null );
	}
	
	public invokevirtual(
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
		return true;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.invokevirtual( this.targetType, this.method );
	}
}