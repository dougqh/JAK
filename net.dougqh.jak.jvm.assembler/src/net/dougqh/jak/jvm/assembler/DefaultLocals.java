package net.dougqh.jak.jvm.assembler;

import java.lang.reflect.Type;
import java.util.Arrays;

import net.dougqh.jak.types.Types;

final class DefaultLocals implements JvmLocals {
	private int maxLocals = 0;
	private Type[] types = new Type[ 8 ];
	
	DefaultLocals() {}
	
	@Override
	public final int declare( final Type type ) {
		int freeSlot = this.findSlot( type );
		
		boolean cat2 = Types.isCategory2( type );
		if ( freeSlot != -1 ) {
			this.types[ freeSlot ] = type;
			if ( cat2 ) {
				this.types[ freeSlot + 1 ] = type;
			}
			return freeSlot;
		} else {
			//To keep things simple, expand regardless of whether it is cat1 or cat2
			if ( this.maxLocals + 2 > this.types.length ) {
				this.types = Arrays.copyOf( this.types, this.types.length << 1 );
			}
			
			int newSlot = this.maxLocals;
			this.types[ this.maxLocals++ ] = type;
			if ( cat2 ) {
				this.types[ this.maxLocals++ ] = Cat2Half.class;
			}
			return newSlot;
		}
	}
	
	private final int findSlot( final Type type ) {
		boolean cat1 = Types.isCategory1( type );
		
		int len = Math.min( this.types.length, this.maxLocals );
		int lookahead = cat1 ? 0 : 1;
		
		for ( int slot = 0; slot < len - lookahead; ++slot ) {
			Type slotType = this.types[ slot ];
			if ( slotType == null ) {
				if ( cat1 ) {
					return slot;
				} else {
					Type nextType = this.types[ slot + 1 ];
					if ( nextType == null ) {
						return slot;
					}
				}
			}
		}
		return -1;
	}
	
	@Override
	public final void undeclare( final int slot ) {
		Type type = this.types[ slot ];
		this.types[ slot ] = null;
		if ( Types.isCategory2( type ) ) {
			this.types[ slot + 1 ] = null;
		}
	}
	
	@Override
	public final Type typeOf( final int slot ) {
		return this.types[ slot ];
	}
	
	@Override
	public final void load( final int slot, final Type type ) {
		this.touch( slot, type );
	}
	
	@Override
	public final void store( final int slot, final Type type ) {
		this.touch( slot, type );
	}

	@Override
	public final void inc( final int slot ) {
		this.touch( slot, int.class );
	}
	
	@Override
	public final int maxLocals() {
		return this.maxLocals;
	}
	
	private final void touch( final int slot, final Type type ) {
		if ( slot + 2 > this.types.length ) {
			this.types = Arrays.copyOf( this.types, this.types.length << 1 );
		}
		
		boolean cat1 = Types.isCategory1( type );
		if ( cat1 ) {
			this.types[ slot ] = type;
			if ( slot >= this.maxLocals ) {
				this.maxLocals = slot + 1;
			}
		} else {
			this.types[ slot ] = type;
			this.types[ slot + 1 ] = Cat2Half.class;
			if ( slot + 1 >= this.maxLocals ) {
				this.maxLocals = slot + 1 + 1;
			}
		}		
	}
	
	private static final class Cat2Half {}
}
