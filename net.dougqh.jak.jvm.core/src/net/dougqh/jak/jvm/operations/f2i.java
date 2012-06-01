package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class f2i extends CastOperation {
	public static final String ID = "f2i";
	public static final byte CODE = F2I;
	
	public static final f2i instance() {
		return new f2i();
	}
	
	private f2i() {}
	
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
		return int.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.f2i();
	}
}