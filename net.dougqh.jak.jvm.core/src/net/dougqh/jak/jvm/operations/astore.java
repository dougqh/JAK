package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Reference;

public final class astore extends StoreOperation {
	public static final String ID = "astore";
	public static final byte CODE = ASTORE;
	
	public static final astore prototype() {
		return new astore( 0 );
	}
	
	private final int slot;
	
	public astore( final int slot ) {
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
		return Reference.class;
	}

	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.astore( this.slot );
	}
}