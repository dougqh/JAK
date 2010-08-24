package net.dougqh.jak;

import java.lang.reflect.Type;

public interface Locals {
	public abstract void local( final int slot, final Type type );
	
	public abstract int addLocal( final Type type );
	
	public abstract int maxLocals();
}
