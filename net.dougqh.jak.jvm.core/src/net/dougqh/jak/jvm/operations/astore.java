package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Slot;
import net.dougqh.jak.types.Reference;

public final class astore extends VariableStoreOperation {
	public static final String ID = "astore";
	public static final byte CODE = ASTORE;
	
	public static final astore prototype() {
		return new astore( 0 );
	}
	
	public astore( final int slot ) {
		super( slot );
	}
	
	public astore( final Slot slot ) {
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
		return Reference.class;
	}

	@Override
	protected final void process( final JvmOperationProcessor processor, final int slot ) {
		processor.astore( slot );
	}
	
	@Override
	protected final void process( final JvmOperationProcessor processor, final Slot slot ) {
		processor.astore( slot );
	}
}