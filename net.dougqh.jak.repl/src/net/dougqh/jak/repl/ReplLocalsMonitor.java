package net.dougqh.jak.repl;

import java.lang.reflect.Type;

import net.dougqh.jak.LocalsMonitor;

public final class ReplLocalsMonitor implements LocalsMonitor {
	private final JakRepl repl;
	private final LocalsMonitor locals;
	
	ReplLocalsMonitor(
		final JakRepl repl,
		final LocalsMonitor locals )
	{
		this.repl = repl;
		this.locals = locals;
	}
	
	@Override
	public final void enableTypeTracking() {
		this.locals.enableTypeTracking();
	}
	
	@Override
	public final Type typeOf( final int slot, final Type expectedType ) {
		return this.locals.typeOf( slot, expectedType );
	}
	
	@Override
	public final void addParameter( final Type type ) {
		this.addParameter( type );
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
		
		this.repl.stateCodeWriter().storeLocal( slot, type );
	}
	
	@Override
	public final int maxLocals() {
		return this.locals.maxLocals();
	}
}
