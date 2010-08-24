package net.dougqh.jak;

import java.lang.reflect.Type;

public interface Stack {
	public abstract void push( final Type type );
	
	public abstract void pop( final Type type );
	
	public abstract int maxStack();
}
