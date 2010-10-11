package net.dougqh.jak.repl;

import java.io.IOException;
import java.lang.reflect.Type;

import net.dougqh.jak.types.Types;

public final class ReplState {
	private final Type[] types;
	private final Object[] values;
	private int nextPos = 0;
	
	ReplState( final int size ) {
		this.types = new Type[ size ];
		this.values = new Object[ size ];
		this.nextPos = 0;
	}
	
	public final boolean isTopCategory1() {
		return Types.isCategory1( this.topType() );
	}
	
	public final boolean isTopCategory2() {
		return Types.isCategory2( this.topType() );
	}
	
	public final Type topType() {
		return this.types[ this.nextPos - 1 ];
	}
	
	public final Object topValue() {
		return this.values[ this.nextPos - 1 ];
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
	
	public final void push( final Type type, final Object value ) {
		this.types[ this.nextPos ] = type;
		this.values[ this.nextPos ] = value;
		++this.nextPos;		
	}
	
	public final void pop() {
		--this.nextPos;
	}
	
	public final void pop2() {
		if ( this.isTopCategory2() ) {
			this.pop();
		} else {
			this.pop();
			this.pop();
		}
	}
	
	public final void swap() {
		swap( this.types, this.nextPos - 2, this.nextPos - 1 );
		swap( this.values, this.nextPos - 2, this.nextPos - 1 );
	}
	
	public final void dup() {
		this.push( this.topType(), this.topValue() );
	}
	
	private static final void swap(
		final Object[] array,
		final int posA,
		final int posB )
	{
		Object temp = array[ posA ];
		array[ posA ] = array[ posB ];
		array[ posB ] = temp;
	}

	final void print( final ReplConsole console ) throws IOException {
		for ( int i = this.nextPos - 1; i >= 0; --i ) {
			Type type = this.types[ i ];
			Object element = this.values[ i ];
			
			console.printColumns(
				ReplUtils.getDisplayName( type ),
				element.toString() );
		}
	}
}
