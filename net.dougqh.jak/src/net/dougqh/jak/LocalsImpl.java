package net.dougqh.jak;

import java.lang.reflect.Type;

final class LocalsImpl implements Locals {
	private int maxLocals = 0;
	
	LocalsImpl() {}
	
	@Override
	public final int addLocal( final Type type ) {
		int slot = this.maxLocals;
		this.local( slot, type );
		return slot;
	}
	
	@Override
	public final void local(
		final int slot,
		final Type type )
	{
		int size = TypeUtils.size( type );
		if ( slot + size > this.maxLocals ) {
			this.maxLocals = slot + size;
		}
	}
	
	@Override
	public final int maxLocals() {
		return this.maxLocals;
	}
}
