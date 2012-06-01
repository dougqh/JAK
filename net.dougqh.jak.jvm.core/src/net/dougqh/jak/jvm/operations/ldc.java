package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Category1;

public final class ldc extends ConstantOperation {
	public static final String ID = "ldc";
	public static final byte CODE = LDC;
	
	public static final ldc prototype() {
		return new ldc( 0 );
	}
	
	private final Object value;
	
	public ldc( final int value ) {
		this.value = value;
	}

	public ldc( final float value ) {
		this.value = value;
	}
	
	public ldc( final String value ) {
		this.value = value;
	}
	
	public ldc( final Type value ) {
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
			processor.ldc( (Integer)this.value );
		} else if ( this.value instanceof Float ) {
			processor.ldc( (Float)this.value );
		} else if ( this.value instanceof String ) {
			processor.ldc( (String)this.value );
		} else if ( this.value instanceof Type ) {
			processor.ldc( (Type)this.value );
		} else {
			throw new IllegalStateException();
		}
	}
}