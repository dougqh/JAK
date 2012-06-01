package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaField;
import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class getstatic extends GetFieldOperation {
	public static final String ID = "getstatic";
	public static final byte CODE = GETSTATIC;
	
	public static final getstatic prototype() {
		return new getstatic( null, null );
	}
	
	private final Type targetType;
	private final JavaField field;
	
	public getstatic(
		final Type targetType,
		final JavaField field )
	{
		this.targetType = targetType;
		this.field = field;
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
		processor.getstatic( this.targetType, this.field );
	}
}