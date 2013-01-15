package net.dougqh.jak.jvm;

import java.lang.reflect.Type;

public interface JvmLocals {
	// Special case used for REPL - should be eliminated
	@Deprecated
	public abstract Type typeOf( final int slot );
	
	public abstract int declare( final Type type );
	
	public abstract void undeclare( final int slot );
	
	public abstract void load( final int slot, final Type type );
	
	public abstract void inc( final int slot );
	
	public abstract void store( final int slot, final Type type );
	
	// Calculation should only be part of the code writer -- not general locals
	@Deprecated
	public abstract int maxLocals();
}
