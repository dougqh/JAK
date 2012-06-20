package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.JvmOperationProcessor.Slot;

public abstract class VariableStoreOperation
	extends StoreOperation
	implements NormalizeableOperation
{
	private final Object slot;
	
	VariableStoreOperation( final int slot ) {
		this.slot = slot;
	}
	
	VariableStoreOperation( final Slot slot ) {
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
	
	@Override
	public final int hashCode() {
		return this.getClass().hashCode() ^ this.slot();
	}
	
	@Override
	public final boolean equals(final Object obj) {
		if ( obj == null ) {
			return false;
		} else if ( obj == this ) {
			return true;
		} else if ( obj.getClass().equals( this.getClass() ) ) {
			VariableStoreOperation that = (VariableStoreOperation)obj;
			return ( this.slot() == that.slot() );
		} else {
			return false;
		}
	}
}
