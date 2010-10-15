package net.dougqh.jak;

public abstract class JakMonitor {
	static final JakMonitor NULL = new JakMonitor() {};
	
	public JavaCoreCodeWriter monitor( final JavaCoreCodeWriter wrappedWriter ) {
		return wrappedWriter;
	}
	
	public LocalsMonitor monitor( final LocalsMonitor locals ) {
		return locals;
	}
	
	public StackMonitor monitor( final StackMonitor stack ) {
		return stack;
	}
}
