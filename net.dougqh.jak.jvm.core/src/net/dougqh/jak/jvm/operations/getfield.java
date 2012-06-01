package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaField;
import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class getfield extends GetFieldOperation {
	public static final String ID = "getfield";
	public static final byte CODE = GETFIELD;
	
	public static final getfield prototype() {
		return new getfield( null, null );
	}
	
	private final Type targetType;
	private final JavaField field;
	
	public getfield(
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
		return false;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.getfield( this.targetType, this.field );
	}
}