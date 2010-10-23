package net.dougqh.jak;

import java.lang.reflect.Type;

import net.dougqh.jak.types.Types;

final class DefaultStackMonitor implements StackMonitor {
	private int curStack = 0;
	private int maxStack = 0;
	
	private JakTypeStack typeStack = null;
	
	DefaultStackMonitor() {}
	
	@Override
	public final int maxStack() {
		return this.maxStack;
	}
	
	@Override
	public final void stack( final Type type ) {
		this.push( Types.size( type ) );

		if ( this.typeStack != null ) {
			this.typeStack.stack( type );
		}
	}
	
	@Override
	public final void unstack( final Type type ) {
		this.pop( Types.size( type ) );
		
		if ( this.typeStack != null ) {
			this.typeStack.unstack( type );
		}
	}
	
	@Override
	public final void pop() {
		this.pop( 1 );
		
		if ( this.typeStack != null ) {
			this.typeStack.pop();
		}
	}
	
	@Override
	public final void pop2() {
		this.pop( 2 );
		
		if ( this.typeStack != null ) {
			this.typeStack.pop2();
		}
	}
	
	@Override
	public final void swap() {
		if ( this.typeStack != null ) {
			this.typeStack.swap();
		}
	}
	
	@Override
	public final void dup() {
		this.push( 1 );
		
		if ( this.typeStack != null ) {
			this.typeStack.dup();
		}
	}
	
	@Override
	public final void dup_x1() {
		this.push( 1 );
		
		if ( this.typeStack != null ) {
			this.typeStack.dup_x1();
		}
	}
	
	@Override
	public final void dup_x2() {
		this.push( 1 );
		
		if ( this.typeStack != null ) {
			this.typeStack.dup_x2();
		}
	}
	
	@Override
	public final void dup2() {
		this.push( 2 );
		
		if ( this.typeStack != null ) {
			this.typeStack.dup2();
		}
	}
	
	@Override
	public final void dup2_x1() {
		this.push( 2 );
		
		if ( this.typeStack != null ) {
			this.typeStack.dup2_x1();
		}
	}
	
	@Override
	public final void dup2_x2() {
		this.push( 2 );
		
		if ( this.typeStack != null ) {
			this.typeStack.dup2_x2();
		}
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
	
	@Override
	public final void enableTypeTracking() {
		this.typeStack = new JakTypeStack();
	}
	
	@Override
	public final JakTypeStack typeStack() {
		if ( this.typeStack == null ) {
			throw new IllegalStateException( "type tracking was not enabled" );
		} else {
			return this.typeStack;
		}
	}
	
	@Override
	public final Type topType( final Type expectedType ) {
		if ( this.typeStack == null ) {
			return expectedType;
		} else {
			return this.typeStack.top();
		}
	}
}
