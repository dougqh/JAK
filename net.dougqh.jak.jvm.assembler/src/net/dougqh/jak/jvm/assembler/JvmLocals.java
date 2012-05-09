package net.dougqh.jak.jvm.assembler;

import java.lang.reflect.Type;

public interface JvmLocals {
	public abstract void enableTypeTracking();
	
	public abstract Type typeOf( final int slot, final Type expectedType );
	
	public abstract void addParameter( final Type type );
	
	public abstract void declare( final int slot, final Type type );
	
	public abstract void load( final int slot, final Type type );
	
	public abstract void inc( final int slot );
	
	public abstract void store( final int slot, final Type type );
	
	public abstract int maxLocals();
}
