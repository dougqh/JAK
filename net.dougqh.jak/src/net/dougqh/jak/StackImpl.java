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
	public final void stack( final Type type ) {
		this.push( Types.size( type ) );
	}
	
	@Override
	public final void unstack( final Type type ) {
		this.pop( Types.size( type ) );
	}
	
	@Override
	public final void pop() {
		this.pop( 1 );
	}
	
	@Override
	public final void pop2() {
		this.pop( 2 );
	}
	
	@Override
	public final void swap() {
	}
	
	@Override
	public final void dup() {
		this.push( 1 );
	}
	
	@Override
	public final void dup_x1() {
		this.push( 1 );
	}
	
	@Override
	public final void dup_x2() {
		this.push( 1 );
	}
	
	@Override
	public final void dup2() {
		this.push( 2 );
	}
	
	@Override
	public final void dup2_x1() {
		this.push( 2 );
	}
	
	@Override
	public final void dup2_x2() {
		this.push( 2 );
	}
	
	private final void push( final int size ) {
		this.curStack += size;
		if ( this.curStack > this.maxStack ) {
			this.maxStack = this.curStack;
		}		
	}
	
	private final void pop( final int size ) {
		this.curStack -= size;
	}
}
