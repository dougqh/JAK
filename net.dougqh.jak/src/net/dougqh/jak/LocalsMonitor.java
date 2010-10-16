package net.dougqh.jak;

import java.lang.reflect.Type;

public interface LocalsMonitor {
	public abstract void load( final int slot, final Type type );
	
	public abstract void inc( final int slot );
	
	public abstract void store( final int slot, final Type type );
	
	public abstract int addLocal( final Type type );
	
	public abstract int maxLocals();
}
