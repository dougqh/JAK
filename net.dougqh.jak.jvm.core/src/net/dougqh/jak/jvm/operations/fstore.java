package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Slot;

public final class fstore extends VariableStoreOperation {
	public static final String ID = "fstore";
	public static final byte CODE = FSTORE;
	
	public static final fstore prototype() {
		return new fstore( 0 );
	}
	
	public fstore( final int slot ) {
		super( slot );
	}
	
	public fstore( final Slot slot ) {
		super( slot );
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
		return float.class;
	}
	
	@Override
	protected final void process( final JvmOperationProcessor processor, final int slot ) {
		processor.fstore( slot );
	}
	
	@Override
	protected final void process( final JvmOperationProcessor processor, final Slot slot ) {
		processor.fstore( slot );
	}
}