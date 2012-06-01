package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class fload extends LoadOperation {
	public static final String ID = "fload";
	public static final byte CODE = FLOAD;
	
	public static final fload prototype() {
		return new fload( 0 );
	}
	
	private final int slot;
	
	public fload( final int slot ) {
		this.slot = slot;
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
	public final boolean isFixed() {
		return false;
	}
	
	@Override
	public final int slot() {
		return this.slot;
	}
	
	@Override
	public final Type type() {
		return float.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.fload( this.slot );
	}
}