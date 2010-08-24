package net.dougqh.jak;

import java.lang.reflect.Type;

import net.dougqh.jak.types.Category2;

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
		if ( isCategory2( type ) ) {
			if ( slot + 2 > this.maxLocals ) {
				this.maxLocals = slot + 2;
			}
		} else {
			if ( slot + 1 > this.maxLocals ) {
				this.maxLocals = slot + 1;
			}
		}
	}
	
	@Override
	public final int maxLocals() {
		return this.maxLocals;
	}
	
	private static final boolean isCategory2( final Type type ) {
		return type.equals( long.class ) ||
			type.equals( double.class ) ||
			type.equals( Category2.class );
	}
}
