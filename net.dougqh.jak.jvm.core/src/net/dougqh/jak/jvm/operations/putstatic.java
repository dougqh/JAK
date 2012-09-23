package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaField;
import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class putstatic extends PutFieldOperation {
	public static final String ID = "putstatic";
	public static final byte CODE = PUTSTATIC;
	
	public static final putstatic prototype() {
		return new putstatic( null, null );
	}
	
	public putstatic( 
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
		return true;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.putstatic( this.targetType, this.field );
	}
}