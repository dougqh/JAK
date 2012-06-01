package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class fstore extends StoreOperation {
	public static final String ID = "fstore";
	public static final byte CODE = FSTORE;
	
	public static final fstore prototype() {
		return new fstore( 0 );
	}
	
	private final int slot;
	
	public fstore( final int slot ) {
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
		return true;
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
		processor.fstore( this.slot );
	}
}