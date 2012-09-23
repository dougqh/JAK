package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaField;
import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Any;
import net.dougqh.jak.types.Reference;

public final class putfield extends PutFieldOperation {
	public static final String ID = "putfield";
	public static final byte CODE = PUTFIELD;
	
	public static final putfield prototype() {
		return new putfield( null, null );
	}
	
	public putfield(
		final Type targetType,
		final JavaField field )
	{
		super( targetType, field );
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
		processor.putfield( this.targetType, this.field );
	}
}