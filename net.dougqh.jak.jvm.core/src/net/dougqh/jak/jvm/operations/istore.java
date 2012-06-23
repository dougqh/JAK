package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Slot;

public final class istore extends VariableStoreOperation {
	public static final String ID = "istore";
	public static final byte CODE = ISTORE;
	
	public static final istore prototype() {
		return new istore( 0 );
	}
	
	public istore( final int slot ) {
		super( slot );
	}
	
	public istore( final Slot slot ) {
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
		return int.class;
	}
	
	@Override
	protected final void process( final JvmOperationProcessor processor, final int slot ) {
		processor.istore( slot );
	}
	
	@Override
	protected final void process( final JvmOperationProcessor processor, final Slot slot ) {
		processor.istore( slot );
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
			processor.istore_0();
			break;
			
			case 1:
			processor.istore_1();
			break;
			
			case 2:
			processor.istore_2();
			break;
			
			case 3:
			processor.istore_3();
			break;
			
			default:
			processor.istore( slot );
			break;
		}
	}
}