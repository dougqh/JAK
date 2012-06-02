package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Slot;
import net.dougqh.jak.types.Reference;

public final class aload extends VariableLoadOperation {
	public static final String ID = "aload";
	public static final byte CODE = ALOAD;
	
	public static final aload prototype() {
		return new aload( 0 );
	}
	
	public aload( final int slot ) {
		super( slot );
	}
	
	public aload( final Slot slot ) {
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
		processor.aload( slot );
	}
	
	@Override
	protected final void process( final JvmOperationProcessor processor, final Slot slot ) {
		processor.aload( slot );
	}
}