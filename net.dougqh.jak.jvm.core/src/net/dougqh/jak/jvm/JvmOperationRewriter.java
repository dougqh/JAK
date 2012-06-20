package net.dougqh.jak.jvm;

import java.util.List;

import net.dougqh.jak.jvm.operations.JvmOperation;

public abstract class JvmOperationRewriter {
	public enum State {
		MISMATCH,
		MATCH,
		FINISH
	}
	
	public abstract void reset();
	
	public abstract State match( final Class<? extends JvmOperation> opClass );
	
	public abstract State match( final JvmOperation jvmOperation );
	
	public abstract void next();
	
	public abstract void finish(
		final JvmOperationProcessor processor,
		final List<? extends JvmOperation> operations );
}
