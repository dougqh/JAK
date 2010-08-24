package net.dougqh.jak;

public interface JakMonitor {
	public abstract JavaCoreCodeWriter monitor( final JavaCoreCodeWriter wrappedWriter );
	
	public abstract Locals monitor( final Locals locals );
	
	public abstract Stack monitor( final Stack stack );
}
