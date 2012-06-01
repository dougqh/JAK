package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class f2l extends CastOperation {
	public static final String ID = "f2l";
	public static final byte CODE = F2L;
	
	public static final f2l instance() {
		return new f2l();
	}
	
	private f2l() {}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}
	
	@Override
	public final Type fromType() {
		return float.class;
	}
	
	@Override
	public final Type toType() {
		return long.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.f2l();
	}
}