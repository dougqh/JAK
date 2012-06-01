package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Category1;

public final class ldc_w extends ConstantOperation {
	public static final String ID = "ldc_w";
	public static final byte CODE = LDC_W;
	
	public static final ldc_w prototype() {
		return new ldc_w( 0 );
	}
	
	private final Object value;
	
	public ldc_w( final int value ) {
		this.value = value;
	}

	public ldc_w( final float value ) {
		this.value = value;
	}
	
	public ldc_w( final String value ) {
		this.value = value;
	}
	
	public ldc_w( final Type value ) {
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
		return Category1.class;
	}
	
	@Override
	public final Type type() {
		return Category1.class;
	}
	
	@Override
	public final <T> T value() {
		return ConstantOperation.<T>as( this.value );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		if ( this.value instanceof Integer ) {
			processor.ldc_w( (Integer)this.value );
		} else if ( this.value instanceof Float ) {
			processor.ldc_w( (Float)this.value );
		} else if ( this.value instanceof String ) {
			processor.ldc_w( (String)this.value );
		} else if ( this.value instanceof Type ) {
			processor.ldc_w( (Type)this.value );
		} else {
			throw new IllegalStateException();
		}
	}
}