package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Slot;

public final class lstore extends VariableStoreOperation {
	public static final String ID = "lstore";
	public static final byte CODE = LSTORE;
	
	public static final lstore prototype() {
		return new lstore( 0 );
	}
	
	public lstore( final int slot ) {
		super( slot );
	}
	
	public lstore( final Slot slot ) {
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
		return long.class;
	}
	
	protected final void process( final JvmOperationProcessor processor, final int slot ) {
		processor.lstore( slot );
	}
	
	@Override
	protected final void process( final JvmOperationProcessor processor, final Slot slot ) {
		processor.lstore( slot );
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
			processor.lstore_0();
			break;
			
			case 1:
			processor.lstore_1();
			break;
			
			case 2:
			processor.lstore_2();
			break;
			
			case 3:
			processor.lstore_3();
			break;
			
			default:
			processor.lstore( slot );
			break;
		}
	}	
}