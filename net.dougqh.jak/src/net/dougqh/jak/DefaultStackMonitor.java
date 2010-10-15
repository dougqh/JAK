package net.dougqh.jak;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.dougqh.jak.types.Types;

final class DefaultStackMonitor implements StackMonitor {
	private int curStack = 0;
	private int maxStack = 0;
	
	private LinkedList< Type > types;
	
	DefaultStackMonitor() {}
	
	@Override
	public final int maxStack() {
		return this.maxStack;
	}
	
	@Override
	public final void stack( final Type type ) {
		this.push( Types.size( type ) );

		if ( this.types != null ) {
			this.types.addFirst( type );
		}
	}
	
	@Override
	public final void unstack( final Type type ) {
		this.pop( Types.size( type ) );
		
		if ( this.types != null ) {
			this.types.removeFirst();
		}
	}
	
	@Override
	public final void pop() {
		this.pop( 1 );
		
		if ( this.types != null ) {
			this.types.removeFirst();
		}
	}
	
	@Override
	public final void pop2() {
		this.pop( 2 );
		
		if ( this.types != null ) {
			Type topType = this.types.removeFirst();
			
			if ( Types.isCategory1( topType ) ) {
				this.types.removeFirst();
			}
		}
	}
	
	@Override
	public final void swap() {
		if ( this.types != null ) {
			Type topType = this.types.removeFirst();
			Type nextType = this.types.removeFirst();
			
			this.types.addFirst( topType );
			this.types.addFirst( nextType );
		}
	}
	
	@Override
	public final void dup() {
		this.push( 1 );
		
		if ( this.types != null ) {
			Type topType = this.types.getFirst();
			this.types.addFirst( topType );
		}
	}
	
	@Override
	public final void dup_x1() {
		this.push( 1 );
		
		//TODO: Implement stack tracking
	}
	
	@Override
	public final void dup_x2() {
		this.push( 1 );
		
		//TODO: Implement stack tracking
	}
	
	@Override
	public final void dup2() {
		this.push( 2 );
		
		Type topType = this.types.getFirst();
		if ( Types.isCategory1( topType ) ) {
			Type nextType = this.types.get( 1 );
			this.types.addFirst( nextType );
			this.types.addFirst( topType );
		} else {
			this.types.addFirst( topType );
		}
	}
	
	@Override
	public final void dup2_x1() {
		this.push( 2 );
		
		//TODO: Implement stack tracking
	}
	
	@Override
	public final void dup2_x2() {
		this.push( 2 );
		
		//TODO: Implement stack tracking
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
		this.types = new LinkedList< Type >();
	}
	
	@Override
	public final List< Type > stackTypes() {
		if ( this.types == null ) {
			throw new IllegalStateException( "stack tracking was not enabled" );
		} else {
			return Collections.unmodifiableList( this.types );
		}
	}
}
