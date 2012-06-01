package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Reference;

public final class aload extends LoadOperation {
	public static final String ID = "aload";
	public static final byte CODE = ALOAD;
	
	public static final aload prototype() {
		return new aload( 0 );
	}
	
	private final int slot;
	
	public aload( final int slot ) {
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
		processor.aload( this.slot );
	}
}