package net.dougqh.jak.jvm;

import java.lang.reflect.Type;

public interface JvmStack {
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
	
	// Special case used for REPL - should be eliminated
	@Deprecated
	public abstract void enableTypeTracking();
	
	// Special case used for REPL - should be eliminated
	@Deprecated
	public abstract JvmTypeStack typeStack();
	
	// Special case used for REPL - should be eliminated
	@Deprecated
	public abstract Type topType( final Type expectedType );
	
	// Calculation should only be part of the code writer -- not general stacks
	@Deprecated
	public abstract int maxStack();
}
