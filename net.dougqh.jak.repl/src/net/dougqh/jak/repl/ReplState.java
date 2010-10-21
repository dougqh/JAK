package net.dougqh.jak.repl;

import java.io.IOException;
import java.lang.reflect.Type;

import net.dougqh.jak.JakStack;
import net.dougqh.jak.types.Types;

public final class ReplState {
	private final Stack stack;
	
	ReplState( final int size ) {
		this.stack = new Stack( size );
	}
	
	public final void push( final int value ) {
		this.push( int.class, value );
	}
	
	public final void push( final long value ) {
		this.push( long.class, value );
	}
	
	public final void push( final float value ) {
		this.push( float.class, value );
	}
	
	public final void push( final double value ) {
		this.push( double.class, value );
	}
	
	public final void push( final Object value ) {
		this.push( Object.class, value );
	}
	
	public final Object pushUninitialized() {
		Object uninitialized = new Uninitialized();
		this.push( uninitialized );
		return uninitialized;
	}
	
	public final void push( final Type type, final Object value ) {
		this.stack.stack( new StackEntry( type, value ) );
	}
	
	public final void pop() {
		this.stack.pop();
	}
	
	public final void pop2() {
		this.stack.pop2();
	}
	
	public final void swap() {
		this.stack.swap();
	}
	
	public final void dup() {
		this.stack.dup();
	}
	
	public final void dup_x1() {
		this.stack.dup_x1();
	}
	
	public final void dup_x2() {
		this.stack.dup_x2();
	}
	
	public final void dup2() {
		this.stack.dup2();
	}
	
	public final void dup2_x1() {
		this.stack.dup2_x1();
	}
	
	public final void dup2_x2() {
		this.stack.dup2_x2();
	}

	final void print( final ReplConsole console ) throws IOException {
		for ( StackEntry entry : this.stack ) {
			console.printColumns(
				ReplFormatter.getDisplayName( entry.type ),
				ReplFormatter.format( entry.value ) );
		}
	}
	
	private static final String toString( final Object value ) {
		if ( value == null ) {
			return "null";
		} else {
			return value.toString();
		}
	}
	
	private static final class Stack extends JakStack< StackEntry > {
		Stack( final int initialCapacity ) {
			super( initialCapacity );
		}
		
		@Override
		protected final boolean isCategory1( final StackEntry value ) {
			return value.isCategory1();
		}
	}
	
	private static final class StackEntry {
		final Type type;
		final Object value;
		
		StackEntry( final Type type, final Object value ) {
			this.type = type;
			this.value = value;
		}
		
		final boolean isCategory1() {
			return Types.isCategory1( this.type );
		}
	}
	
	private static final class Uninitialized {
		@Override
		public final String toString() {
			return "<Uninitialized Object>";
		}
	}
}
