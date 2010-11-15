package net.dougqh.jak.assembler;

public final class JakConfiguration {
	private boolean stackTracking = false;
	private JakMonitor monitor = JakMonitor.NULL;
	
	public final JakConfiguration enableTypeTracking() {
		this.stackTracking = true;
		return this;
	}
	
	public final JakConfiguration monitor( final JakMonitor monitor ) {
		this.monitor = monitor;
		return this;
	}
	
	final LocalsMonitor configure( final LocalsMonitor locals ) {
		LocalsMonitor wrappedLocals = this.monitor.monitor( locals );
		if ( this.stackTracking ) {
			wrappedLocals.enableTypeTracking();
		}
		return wrappedLocals;
	}
	
	final StackMonitor configure( final StackMonitor stack ) {
		StackMonitor wrappedStack = this.monitor.monitor( stack );
		if ( this.stackTracking ) {
			wrappedStack.enableTypeTracking();
		}
		return wrappedStack;
	}
	
	final JavaCoreCodeWriter configure( final JavaCoreCodeWriter codeWriter ) {
		return this.monitor.monitor( codeWriter );
	}
}
