package net.dougqh.jak.repl;

import java.io.IOException;

public final class ReplState {
	private final Object[] stack;
	private int nextPos = 0;
	
	ReplState( final int size ) {
		this.stack = new Object[ size ];
		this.nextPos = 0;
	}
	
	public final void push( final int value ) {
		this.stack[ this.nextPos++ ] = value;
	}
	
	public final void push( final long value ) {
		this.stack[ this.nextPos++ ] = value;
	}
	
	public final void push( final float value ) {
		this.stack[ this.nextPos++ ] = value;
	}
	
	public final void push( final double value ) {
		this.stack[ this.nextPos++ ] = value;
	}
	
	public final void push( final Object value ) {
		this.stack[ this.nextPos++ ] = value;
	}
	
	public final void pop() {
		--this.nextPos;
	}

	final void print( final ReplConsole console ) throws IOException {
		for ( int i = this.nextPos - 1; i >= 0; --i ) {
			Object element = this.stack[ i ];
			
			console.append( element.toString() ).endl();
		}
	}
}
