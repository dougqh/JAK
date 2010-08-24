package net.dougqh.jak.repl;

import java.io.IOException;

import jline.ConsoleReader;

public final class ReplStack {
	private final Object[] stack;
	private int nextPos = 0;
	
	ReplStack( final int size ) {
		this.stack = new Object[ size ];
		this.nextPos = 0;
	}
	
	public final void push( final int value ) {
		this.stack[ this.nextPos++ ] = value;
	}

	final void print( final ConsoleReader reader ) throws IOException {
		for ( int i = this.nextPos - 1; i >= 0; --i ) {
			Object element = this.stack[ i ];
			
			reader.printString( element.toString() );
			reader.printNewline();
		}
	}
}
