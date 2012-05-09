package net.dougqh.jak.jvm.assembler;

import java.lang.reflect.Type;
import java.util.Arrays;

import net.dougqh.jak.types.Types;

final class DefaultLocalsMonitor implements JvmLocals {
	private int maxLocals = 0;
	private Type[] types;
	
	DefaultLocalsMonitor() {}
	
	@Override
	public void enableTypeTracking() {
		this.types = new Type[ 8 ];
	}
	
	@Override
	public final Type typeOf( final int slot, final Type expectedType ) {
		if ( this.types != null ) {
			return this.types[ slot ];
		} else {
			return expectedType;
		}
	}
	
	@Override
	public final void addParameter( final Type type ) {
		this.local( this.maxLocals, type );
	}

	@Override
	public final void inc( final int slot ) {
		this.local( slot, int.class );
	}
	
	@Override
	public final void declare( final int slot, final Type type ) {
		this.local( slot, type );
	}
	
	@Override
	public final void load( final int slot, final Type type ) {
		this.local( slot, type );
	}
	
	@Override
	public final void store( final int slot, final Type type ) {
		this.local( slot, type );
	}
	
	@Override
	public final int maxLocals() {
		return this.maxLocals;
	}
	
	private final void local( final int slot, final Type type ) {
		int size = Types.size( type );
		if ( slot + size > this.maxLocals ) {
			this.maxLocals = slot + size;
		}
		
		if ( this.types != null ) {
			if ( slot >= this.types.length ) {
				this.types = Arrays.copyOf( this.types, this.types.length << 1 );
			}
			this.types[ slot ] = type;
		}
	}
}
