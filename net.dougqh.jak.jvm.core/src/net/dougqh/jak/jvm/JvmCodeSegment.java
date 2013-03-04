package net.dougqh.jak.jvm;

import net.dougqh.jak.jvm.operations.JvmOperation;

public interface JvmCodeSegment {
	public abstract Iterable<JvmOperation> operations();
	
	public abstract void process(final JvmOperationProcessor processor);
	
	public abstract void process(final SimpleJvmOperationProcessor processor);
}
