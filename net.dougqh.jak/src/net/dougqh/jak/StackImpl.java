package net.dougqh.jak;

import java.lang.reflect.Type;

import net.dougqh.jak.types.Category2;

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
		if ( isCategory2( type ) ) {
			this.push2();
		} else {
			this.push();
		}
	}
	
	@Override
	public final void pop(Type type) {
		if ( isCategory2( type ) ) {
			this.pop2();
		} else {
			this.pop();
		}		
	}
	
	private final void push() {
		this.curStack += 1;
		if ( this.curStack > this.maxStack ) {
			this.maxStack = this.curStack;
		}
	}
	
	private final void push2() {
		this.curStack += 2;
		if ( this.curStack > this.maxStack ) {
			this.maxStack = this.curStack;
		}
	}
	
	private final void pop() {
		this.curStack -= 1;
	}
	
	private final void pop2() {
		this.curStack -= 2;
	}
	
	private static final boolean isCategory2( final Type type ) {
		return type.equals( long.class ) ||
			type.equals( double.class ) ||
			type.equals( Category2.class );
	}
}
