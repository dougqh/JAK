package net.dougqh.jak;

import java.lang.reflect.Type;

import net.dougqh.jak.types.Types;

final class DefaultLocalsMonitor implements LocalsMonitor {
	private int maxLocals = 0;
	
	DefaultLocalsMonitor() {}
	
	@Override
	public final int addLocal( final Type type ) {
		int slot = this.maxLocals;
		this.local( slot, type );
		return slot;
	}
	
	@Override
	public final void inc( final int slot ) {
		this.local( slot, int.class );
	}
	
	@Override
	public final void load( final int slot, final Type type ) {
		this.local( slot, type );
	}
	
	@Override
	public final void store( final int slot, final Type type ) {
		this.local( slot, type );
	}
	
	private final void local( final int slot, final Type type ) {
		int size = Types.size( type );
		if ( slot + size > this.maxLocals ) {
			this.maxLocals = slot + size;
		}
	}
	
	@Override
	public final int maxLocals() {
		return this.maxLocals;
	}
}
