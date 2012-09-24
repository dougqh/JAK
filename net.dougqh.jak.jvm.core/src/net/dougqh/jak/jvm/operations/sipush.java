package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class sipush extends ConstantOperation {
	public static final String ID = "sipush";
	public static final byte CODE = SIPUSH;
	
	public static final sipush prototype() {
		return new sipush( (short)0 );
	}
	
	private final short value;
	
	public sipush( final int value ) {
		if ( value < Short.MIN_VALUE || value > Short.MAX_VALUE ) {
			throw new IllegalArgumentException();
		}
		this.value = (short)value;
	}
	
	public sipush( final short value ) {
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
		return short.class;
	}
	
	@Override
	public final Type type() {
		return int.class;
	}
	
	@Override
	public final <T> T value() {
		return ConstantOperation.<T>as( Integer.valueOf( this.value ) );
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.sipush( this.value );
	}
}