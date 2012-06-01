package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public final class lload extends LoadOperation {
	public static final String ID = "lload";
	public static final byte CODE = LLOAD;
	
	public static final lload prototype() {
		return new lload( 0 );
	}
	
	private final int slot;
	
	public lload( final int slot ) {
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
		return long.class;
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.lload( this.slot );
	}
}