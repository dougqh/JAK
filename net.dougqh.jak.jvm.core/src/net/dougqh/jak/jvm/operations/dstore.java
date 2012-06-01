package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class dstore extends StoreOperation {
	public static final String ID = "dstore";
	public static final byte CODE = DSTORE;
	
	public static final dstore prototype() {
		return new dstore( 0 );
	}
	
	private final int slot;
	
	public dstore( final int slot ) {
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
	public final Type type() {
		return double.class;
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
	public final void process( final JvmOperationProcessor processor ) {
		processor.dstore( this.slot );
	}
}