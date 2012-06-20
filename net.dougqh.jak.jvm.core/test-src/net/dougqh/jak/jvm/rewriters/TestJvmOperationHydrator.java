package net.dougqh.jak.jvm.rewriters;

import net.dougqh.jak.jvm.JvmOperationHydrator;
import net.dougqh.jak.jvm.operations.JvmOperation;

public final class TestJvmOperationHydrator extends JvmOperationHydrator {
	private JvmOperation lastOperation = null;
	
	@Override
	protected final void add(final JvmOperation operation) {
		this.lastOperation = operation;
	}
	
	public final JvmOperation last() {
		return this.lastOperation;
	}
}
