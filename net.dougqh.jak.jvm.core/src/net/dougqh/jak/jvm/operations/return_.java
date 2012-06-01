package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class return_ extends ReturnOperation {
	public static final String ID = "return";
	public static final byte CODE = RETURN;
	
	public static final return_ instance() {
		return new return_();
	}
	
	private return_() {}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final Type type() {
		return void.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.return_();
	}
}