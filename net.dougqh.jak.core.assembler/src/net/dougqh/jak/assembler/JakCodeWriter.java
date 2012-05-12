package net.dougqh.jak.assembler;

import java.lang.reflect.Type;

public interface JakCodeWriter {
	public abstract JakCodeWriter startScope();
	
	public abstract JakCodeWriter declare( final Type type, final String var );
	
	public abstract JakCodeWriter alias( final String var, final String existingVar );
	
	public abstract JakCodeWriter endScope();
	
	public abstract JakCodeWriter startLabelScope();
	
	public abstract JakCodeWriter label( final String label );
	
	public abstract JakCodeWriter endLabelScope();
	
	public abstract JakCodeWriter if_( final JakCondition condition, final String label );
	
	public abstract JakCodeWriter macro( final JakMacro macro );
	
	public abstract JakCodeWriter scope( final JakMacro macro );
}
