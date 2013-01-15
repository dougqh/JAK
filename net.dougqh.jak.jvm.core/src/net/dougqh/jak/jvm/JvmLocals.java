package net.dougqh.jak.jvm;

import java.lang.reflect.Type;

public interface JvmLocals {
	public abstract Type typeOf( final int slot, final Type expectedType );
	
	// Probably should not be on all locals interfaces
	@Deprecated
	public abstract int declare( final Type type );
	
	// Probably should not be on all local interfaces
	@Deprecated
	public abstract void undeclare( final int slot );
	
	public abstract void load( final int slot, final Type type );
	
	// Probably should not be on all local interfaces
	@Deprecated
	public abstract void inc( final int slot );
	
	public abstract void store( final int slot, final Type type );
	
	// Calculation should only be part of the code writer -- not general locals
	@Deprecated
	public abstract int maxLocals();
}
