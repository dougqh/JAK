package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public interface NormalizeableOperation extends JvmOperation {
	public boolean canNormalize();
	
	public void normalize( final JvmOperationProcessor processor );
}
