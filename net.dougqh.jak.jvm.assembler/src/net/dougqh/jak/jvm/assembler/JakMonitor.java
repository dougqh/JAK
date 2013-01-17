package net.dougqh.jak.jvm.assembler;

import net.dougqh.jak.jvm.JvmLocalsTracker;
import net.dougqh.jak.jvm.JvmStackTracker;

public abstract class JakMonitor {
	static final JakMonitor NULL = new JakMonitor() {};
	
	public JvmCoreCodeWriter monitor( final JvmCoreCodeWriter wrappedWriter ) {
		return wrappedWriter;
	}
	
	public JvmLocalsTracker monitor( final JvmLocalsTracker locals ) {
		return locals;
	}
	
	public JvmStackTracker monitor( final JvmStackTracker stack ) {
		return stack;
	}
}
