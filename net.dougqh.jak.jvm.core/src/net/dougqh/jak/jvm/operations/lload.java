package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Slot;

public final class lload extends VariableLoadOperation {
	public static final String ID = "lload";
	public static final byte CODE = LLOAD;
	
	public static final lload prototype() {
		return new lload( 0 );
	}
	
	public lload( final int slot ) {
		super( slot );
	}
	
	public lload( final Slot slot ) {
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
	
	@Override
	protected final void process( final JvmOperationProcessor processor, final int slot ) {
		processor.lload( slot );
	}
	
	@Override
	protected final void process( final JvmOperationProcessor processor, final Slot slot ) {
		processor.lload( slot );
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
			processor.lload_0();
			break;
			
			case 1:
			processor.lload_1();
			break;
			
			case 2:
			processor.lload_2();
			break;
			
			case 3:
			processor.lload_3();
			break;
			
			default:
			processor.lload( slot );
			break;
		}
	}
}