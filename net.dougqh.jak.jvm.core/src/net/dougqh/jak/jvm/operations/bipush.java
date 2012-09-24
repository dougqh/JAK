package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class bipush extends ConstantOperation {
	public static final String ID = "bipush";
	public static final byte CODE = BIPUSH;
	
	public static final bipush prototype() {
		return new bipush( (byte)0 );
	}
	
	private final byte value;
	
	public bipush( final int value ) {
		if ( value < Byte.MIN_VALUE || value > Byte.MAX_VALUE ) {
			throw new IllegalArgumentException();
		}
		this.value = (byte)value;
	}
	
	public  bipush( final byte value ) {
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
		return byte.class;
	}
	
	@Override
	public final Type type() {
		return int.class;
	}
	
	@Override
	public <T> T value() {
		return ConstantOperation.<T>as(Integer.valueOf(this.value));
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.bipush( this.value );
	}
}