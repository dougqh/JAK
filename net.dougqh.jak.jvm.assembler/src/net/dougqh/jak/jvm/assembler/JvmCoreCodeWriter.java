package net.dougqh.jak.jvm.assembler;

import net.dougqh.jak.jvm.JvmLocalsTracker;
import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmStackTracker;


public interface JvmCoreCodeWriter extends JvmOperationProcessor {
	public interface DeferredWrite {
		public abstract void write(
			final JvmCoreCodeWriter coreWriter,
			final boolean terminal );
	}
	
	//Deferred write methods
	public abstract void defer( final DeferredWrite deferredWrite );
	
	public abstract void prepare();
	
	public abstract WritingContext context();
	
	public abstract void finish();
	
	public abstract JvmStackTracker stackMonitor();
	
	public abstract JvmLocalsTracker localsMonitor();
	
	public abstract int pos();
}