package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Slot;

abstract class VariableLoadOperation extends LoadOperation {
	private final Object slot;
	
	VariableLoadOperation( final int slot ) {
		this.slot = slot;
	}
	
	VariableLoadOperation( final Slot slot ) {
		this.slot = slot;
	}
	
	@Override
	public final boolean isFixed() {
		return false;
	}
	
	@Override
	public final int slot() {
		if ( this.slot instanceof Integer ) {
			Integer slot = (Integer)this.slot;
			return slot;
		} else if ( this.slot instanceof Slot ) {
			Slot slot = (Slot)this.slot;
			return slot.pos();
		} else {
			throw new IllegalStateException();
		}
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		if ( this.slot instanceof Integer ) {
			this.process( processor, (Integer)this.slot );
		} else if ( this.slot instanceof Slot ) {
			this.process( processor, (Slot)this.slot );
		} else {
			throw new IllegalStateException();
		}
	}
	
	protected abstract void process(
		final JvmOperationProcessor processor,
		final int slot );
	
	protected abstract void process(
		final JvmOperationProcessor processor,
		final Slot slot );
}
