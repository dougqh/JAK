package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Slot;

public final class iload extends VariableLoadOperation {
	public static final String ID = "iload";
	public static final byte CODE = ILOAD;
	
	public static final iload prototype() {
		return new iload( 0 );
	}
	
	public iload( final int slot ) {
		super( slot );
	}
	
	public iload( final Slot slot ) {
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
		processor.iload( slot );
	}
	
	@Override
	protected final void process( final JvmOperationProcessor processor, final Slot slot ) {
		processor.iload( slot );
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
			processor.iload_0();
			
			case 1:
			processor.iload_1();
			
			case 2:
			processor.iload_2();
			
			case 3:
			processor.iload_3();
			
			default:
			processor.iload( slot );
		}
	}
}