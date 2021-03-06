package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Slot;

public final class dstore extends VariableStoreOperation {
	public static final String ID = "dstore";
	public static final byte CODE = DSTORE;
	
	public static final dstore prototype() {
		return new dstore( 0 );
	}

	public dstore( final int slot ) {
		super( slot );
	}
	
	public dstore( final Slot slot ) {
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
		return double.class;
	}
	
	@Override
	protected final void process( final JvmOperationProcessor processor, final int slot ) {
		processor.dstore( slot );
	}
	
	@Override
	protected final void process( final JvmOperationProcessor processor, final Slot slot ) {
		processor.dstore( slot );
	}
	
	
	@Override
	public final boolean canNormalize() {
		return ( this.slot() < 4 );
	}
	
	@Override
	public final void normalize( final JvmOperationProcessor processor ) {
		normalize( processor, this.slot() );
	}
	
	public static final void normalize(
		final JvmOperationProcessor processor,
		final int slot )
	{
		switch ( slot ) {
			case 0:
			processor.dstore_0();
			break;
			
			case 1:
			processor.dstore_1();
			break;
			
			case 2:
			processor.dstore_2();
			break;
			
			case 3:
			processor.dstore_3();
			break;
			
			default:
			processor.dstore( slot );
			break;
		}
	}	
}