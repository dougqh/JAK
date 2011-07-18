package net.dougqh.jak.jvm.assembler;

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
	
	public final JvmLocals configure( final JvmLocals locals ) {
		JvmLocals wrappedLocals = this.monitor.monitor( locals );
		if ( this.stackTracking ) {
			wrappedLocals.enableTypeTracking();
		}
		return wrappedLocals;
	}
	
	public final JvmStack configure( final JvmStack stack ) {
		JvmStack wrappedStack = this.monitor.monitor( stack );
		if ( this.stackTracking ) {
			wrappedStack.enableTypeTracking();
		}
		return wrappedStack;
	}
	
	final JvmCoreCodeWriter configure( final JvmCoreCodeWriter codeWriter ) {
		return this.monitor.monitor( codeWriter );
	}
}
