package net.dougqh.jak.repl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import net.dougqh.jak.assembler.JakStack;
import net.dougqh.jak.types.Types;

public final class ReplState {
	private final Stack stack;
	private final Locals locals;
	
	ReplState( final int stackSize, final int localsSize ) {
		this.stack = new Stack( stackSize );
		this.locals = new Locals( localsSize );
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
	
	public final Object pushUninitialized( final Type type ) {
		Object uninitialized = new Uninitialized( type );
		this.push( Object.class, uninitialized );
		return uninitialized;
	}
	
	public final void push( final Type type, final Object value ) {
		this.stack.stack( new JvmValue( type, value ) );
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
	
	public final void storeLocal( final int slot, final int value ) {
		this.storeLocal( slot, int.class, value );
	}
	
	public final void storeLocal( final int slot, final float value ) {
		this.storeLocal( slot, float.class, value );
	}
	
	public final void storeLocal( final int slot, final long value ) {
		this.storeLocal( slot, long.class, value );
	}
	
	public final void storeLocal( final int slot, final double value ) {
		this.storeLocal( slot, double.class, value );
	}
	
	public final void storeLocal( final int slot, final Type type, final Object value ) {
		this.locals.set( slot, new JvmValue( type, value ) );
	}
	
	public final void incLocal( final int slot ) {
		this.locals.inc( slot );
	}

	final void printStack( final ReplConsole console ) throws IOException {
		console.println( "STACK" );
		for ( JvmValue jvmValue : this.stack ) {
			console.printColumns(
				ReplFormatter.getDisplayName( jvmValue.type ),
				ReplFormatter.format( jvmValue.value ) );
		}
	}

	final void printLocals( final ReplConsole console ) throws IOException {
		console.println( "LOCALS" );
		for ( JvmSlot slot : this.locals ) {
			console.printColumns(
				Integer.toString( slot.index ),
				ReplFormatter.getDisplayName( slot.type ),
				ReplFormatter.format( slot.value ) );
		}
	}

	private static final class Stack extends JakStack< JvmValue > {
		Stack( final int initialCapacity ) {
			super( initialCapacity );
		}
		
		@Override
		protected final boolean isCategory1( final JvmValue value ) {
			return value.isCategory1();
		}
	}

	private static final class Locals implements Iterable< JvmSlot > {
		private JvmValue[] values;
		
		Locals( final int initialCapacity ) {
			this.values = new JvmValue[ initialCapacity ];
		}
		
		final void set( final int slot, final JvmValue value ) {
			this.ensureCapacity( slot );
			
			this.values[ slot ] = value;
		}
		
		final void inc( final int slot ) {
			this.values[ slot ] = this.values[ slot ].inc();
		}
		
		@Override
		public final Iterator< JvmSlot > iterator() {
			ArrayList< JvmSlot > slots = new ArrayList< JvmSlot >( this.values.length );
			for ( int i = 0; i < this.values.length; ++i ) {
				JvmValue value = this.values[ i ];
				if ( value != null ) {
					slots.add( new JvmSlot( i, value ) );
				}
			}
			return Collections.unmodifiableList( slots ).iterator();
		}
		
		private final void ensureCapacity( final int slot ) {
			if ( slot >= this.values.length ) {
				this.values = Arrays.copyOf( this.values, this.values.length << 1 );
			}
		}
	}
	
	private static final class JvmValue {
		final Type type;
		final Object value;
		
		JvmValue( final Type type, final Object value ) {
			this.type = type;
			this.value = value;
		}
		
		final JvmValue inc() {
			return new JvmValue( type, (Integer)this.value + 1 );
		}
		
		final boolean isCategory1() {
			return Types.isCategory1( this.type );
		}
	}
	
	private static final class JvmSlot {
		final int index;
		final Type type;
		final Object value;
		
		JvmSlot( final int index, final JvmValue value ) {
			this.index = index;
			this.type = value.type;
			this.value = value.value;
		}
	}
	
	private static final class Uninitialized {
		private final Type type;
		
		Uninitialized( final Type type ) {
			this.type = type;
		}
		
		@Override
		public final String toString() {
			return "<Uninitialized " + ReplFormatter.getDisplayName( this.type ) + ">";
		}
	}
}
