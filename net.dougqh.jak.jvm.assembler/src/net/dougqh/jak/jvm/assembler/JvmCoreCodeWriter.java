package net.dougqh.jak.jvm.assembler;

import net.dougqh.jak.jvm.JvmLocals;
import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmStack;


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
	
	public abstract JvmStack stackMonitor();
	
	public abstract JvmLocals localsMonitor();
	
	public abstract int pos();
}