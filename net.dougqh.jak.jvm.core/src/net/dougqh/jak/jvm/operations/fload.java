package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Slot;

public final class fload extends VariableLoadOperation {
	public static final String ID = "fload";
	public static final byte CODE = FLOAD;
	
	public static final fload prototype() {
		return new fload( 0 );
	}
	
	public fload( final int slot ) {
		super( slot );
	}
	
	public fload( final Slot slot ) {
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
		processor.fload( slot );
	}
	
	@Override
	protected final void process( final JvmOperationProcessor processor, final Slot slot ) {
		processor.fload( slot );
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
			processor.fload_0();
			break;
			
			case 1:
			processor.fload_1();
			break;
			
			case 2:
			processor.fload_2();
			break;
			
			case 3:
			processor.fload_3();
			break;
			
			default:
			processor.fload( slot );
			break;
		}
	}
}