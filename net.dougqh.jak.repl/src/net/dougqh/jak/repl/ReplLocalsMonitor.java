package net.dougqh.jak.repl;

import java.lang.reflect.Type;

import net.dougqh.jak.LocalsMonitor;

public final class ReplLocalsMonitor implements LocalsMonitor {
	private final LocalsMonitor locals;
	
	ReplLocalsMonitor( final LocalsMonitor locals ) {
		this.locals = locals;
	}
	
	@Override
	public final int addLocal( final Type type ) {
		return this.locals.addLocal( type );
	}
	
	@Override
	public final void inc( final int slot ) {
		this.locals.inc( slot );
	}
	
	@Override
	public final void load( final int slot, final Type type ) {
		this.locals.load( slot, type );
	}
	
	@Override
	public final void store( final int slot, final Type type ) {
		this.locals.store( slot, type );
	}
	
	@Override
	public final int maxLocals() {
		return this.locals.maxLocals();
	}
}
