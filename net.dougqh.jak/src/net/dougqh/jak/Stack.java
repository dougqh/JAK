package net.dougqh.jak;

import java.lang.reflect.Type;

public interface Stack {
	public abstract void stack( final Type type );
	
	public abstract void unstack( final Type type );
	
	public abstract void pop();
	
	public abstract void pop2();
	
	public abstract void swap();
	
	public abstract void dup();
	
	public abstract void dup_x1();
	
	public abstract void dup_x2();
	
	public abstract void dup2();
	
	public abstract void dup2_x1();
	
	public abstract void dup2_x2();
	
	public abstract int maxStack();
}
