package net.dougqh.jak.jvm.assembler;

import net.dougqh.jak.jvm.JvmLocals;
import net.dougqh.jak.jvm.JvmStack;

public final class JakConfiguration {
	private boolean typeTracking = false;
	private JakMonitor monitor = JakMonitor.NULL;
	
	public final JakConfiguration enableTypeTracking() {
		this.typeTracking = true;
		return this;
	}
	
	public final JakConfiguration monitor( final JakMonitor monitor ) {
		this.monitor = monitor;
		return this;
	}
	
	public final JvmLocals configure( final JvmLocals locals ) {
		return this.monitor.monitor( locals );
	}
	
	public final JvmStack configure( final JvmStack stack ) {
		JvmStack wrappedStack = this.monitor.monitor( stack );
		if ( this.typeTracking ) {
			wrappedStack.enableTypeTracking();
		}
		return wrappedStack;
	}
	
	final JvmCoreCodeWriter configure( final JvmCoreCodeWriter codeWriter ) {
		return this.monitor.monitor( codeWriter );
	}
}
