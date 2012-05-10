package net.dougqh.jak.jvm.assembler;

import java.lang.reflect.Type;

public interface JvmLocals {
	public abstract Type typeOf( final int slot );
	
	public abstract int declare( final Type type );
	
	public abstract void undeclare( final int slot );
	
	public abstract void load( final int slot, final Type type );
	
	public abstract void inc( final int slot );
	
	public abstract void store( final int slot, final Type type );
	
	public abstract int maxLocals();
}
