package net.dougqh.jak;

import java.lang.reflect.Type;

import net.dougqh.jak.types.Types;

final class StackImpl implements Stack {
	private int curStack = 0;
	private int maxStack = 0;
	
	StackImpl() {}
	
	@Override
	public final int maxStack() {
		return this.maxStack;
	}
	
	@Override
	public final void push( final Type type ) {
		this.curStack += Types.size( type );
		if ( this.curStack > this.maxStack ) {
			this.maxStack = this.curStack;
		}
	}
	
	@Override
	public final void pop( final Type type ) {
		this.curStack -= Types.size( type );
	}
}
