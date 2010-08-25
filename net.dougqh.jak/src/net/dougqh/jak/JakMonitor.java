package net.dougqh.jak;

public abstract class JakMonitor {
	public JavaCoreCodeWriter monitor( final JavaCoreCodeWriter wrappedWriter ) {
		return wrappedWriter;
	}
	
	public Locals monitor( final Locals locals ) {
		return locals;
	}
	
	public Stack monitor( final Stack stack ) {
		return stack;
	}
}
