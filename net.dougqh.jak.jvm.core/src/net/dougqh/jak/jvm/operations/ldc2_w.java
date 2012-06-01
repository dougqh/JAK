package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Category2;

public final class ldc2_w extends ConstantOperation {
	public static final String ID = "ldc2_w";
	public static final byte CODE = LDC2_W;
	
	public static final ldc2_w prototype() {
		return new ldc2_w( 0 );
	}
	
	private final Object value;
	
	public ldc2_w( final long value ) {
		this.value = value;
	}
	
	public ldc2_w( final double value ) {
		this.value = value;
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
	public final Type codeInputType() {
		return Category2.class;
	}
	
	@Override
	public final Type type() {
		return Category2.class;
	}
	
	@Override
	public final <T> T value() {
		return ConstantOperation.<T>as( this.value );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		if ( this.value instanceof Long ) {
			processor.ldc2_w( (Long)value );
		} else if ( this.value instanceof Double ) {
			processor.ldc2_w( (Double)value );
		} else {
			throw new IllegalStateException();
		}
	}
}