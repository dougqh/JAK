package net.dougqh.jak.assembler;

import java.lang.reflect.Type;

public interface StackMonitor {
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
	
	public abstract void enableTypeTracking();
	
	public abstract JakTypeStack typeStack();
	
	public abstract Type topType( final Type expectedType );
	
	public abstract int maxStack();
}
