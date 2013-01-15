package net.dougqh.jak.jvm;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

//TODO: This is mess -- desparately needs to be cleaned up
public abstract class BaseJvmStack< T > implements JvmStack, Iterable< T > {
	private T[] stack;
	private int size = 0;
	
	protected BaseJvmStack() {
		this(8);
	}
	
	@SuppressWarnings( "unchecked" )
	protected BaseJvmStack( final int initialCapacity ) {
		this.stack = (T[])new Object[ initialCapacity ];
	}
	
	@Override
	public final void enableTypeTracking() {
		//TODO: remove
	}
	
	@Override
	public final int maxStack() {
		// TODO: remove
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final void stack(Type type) {
		this.stackT(this.fromType(type));
	}
	
	@Override
	public void unstack(Type type) {
		this.unstackT(this.fromType(type));
	}
	
	protected abstract T fromType(final Type type);
	
	@Override
	public JvmTypeStack typeStack() {
		// TODO: Horrific hack overridden by JvmTypeStack
		return null;
	}
	
	@Override
	public Type topType(Type expectedType) {
		return expectedType;
	}
	
	public final int size() {
		return this.size;
	}
	
	public final boolean isEmpty() {
		return ( this.size == 0 );
	}
	
	public final T top() {
		return this.getFromTop( 0 );
	}
	
	public final void stackT( final T value ) {
		this.stackImpl( value );		
	}
	
	public final void unstackT( final T value ) {
		this.unstackImpl();
	}
	
	public final void pop() {
		this.unstackImpl();
	}
	
	public final void pop2() {
		T value = this.unstackImpl();
		if ( this.isCategory1( value ) ) {
			this.unstackImpl();
		}
	}
	
	public final void swap() {
		T temp = this.getFromTop( 0 );
		this.setFromTop( 0, this.getFromTop( 1 ) );
		this.setFromTop( 1, temp );
	}
	
	public final void dup() {
		T top = this.getFromTop( 0 );
		this.stackImpl( top );
	}
	
	public final void dup_x1() {
		T top = this.getFromTop( 0 );
		this.insertFromTop( 1, top );
	}
	
	public final void dup_x2() {
		T top = this.getFromTop( 0 );
		T nextToTop = this.getFromTop( 1 );
		
		if ( this.isCategory1( nextToTop ) ) {
			this.insertFromTop( 2, top );
		} else {
			this.insertFromTop( 1, top );
		}
	}
	
	public final void dup2() {
		T top = this.getFromTop( 0 );
		if ( this.isCategory1( top ) ) {
			T nextToTop = this.getFromTop( 1 );
			this.stackImpl( nextToTop );
			this.stackImpl( top );
		} else {
			this.stackImpl( top );
		}
	}
	
	public final void dup2_x1() {
		T top = this.getFromTop( 0 );
		if ( this.isCategory1( top ) ) {
			T nextToTop = this.getFromTop( 1 );
			this.insertPairFromTop( 2, top, nextToTop );
		} else {
			this.insertFromTop( 1, top );
		}
	}
	
	public final void dup2_x2() {
		T top = this.getFromTop( 0 );
		if ( this.isCategory1( top ) ) {
			T firstFromTop = this.getFromTop( 1 );
			
			T secondFromTop = this.getFromTop( 2 );
			if ( this.isCategory1( secondFromTop ) ) {
				this.insertPairFromTop( 3, top, firstFromTop );
			} else {
				this.insertPairFromTop( 2, top, firstFromTop );
			}
		} else {
			T firstFromTop = this.getFromTop( 1 );
			if ( this.isCategory1( firstFromTop ) ) {
				this.insertFromTop( 2, top );
			} else {
				this.insertFromTop( 1, top );
			}
		}
	}
	
	@Override
	public final Iterator< T > iterator() {
		return new IteratorImpl();
	}

	protected abstract boolean isCategory1( final T value );
	
	private final void stackImpl( final T value ) {
		this.ensureCapacity( this.size + 1 );
		
		this.stack[ this.size++ ] = value;
	}
	
	private final T unstackImpl() {
		return this.stack[ --this.size ];
	}
	
	private final T getFromTop( final int offset ) {
		return this.stack[ this.fromTopIndex( offset ) ];
	}
	
	private final void setFromTop( final int offset, final T value ) {
		this.stack[ this.fromTopIndex( offset ) ] = value;
	}
	
	private final void insertFromTop( final int offset, final T value ) {
		this.ensureCapacity( this.size + 1 );
		
		int index = this.fromTopIndex( offset );
		int length = this.size - index;
		System.arraycopy(
			this.stack, index,
			this.stack, index + 1,
			length );
		this.stack[ index ] = value;
		
		++this.size;
	}
	
	private final void insertPairFromTop( final int offset, final T value1, final T value2 ) {
		this.ensureCapacity( this.size + 2 );
		
		int index = this.fromTopIndex( offset );
		int length = this.size - index;
		System.arraycopy(
			this.stack, index,
			this.stack, index + 2,
			length );
		this.stack[ index ] = value2;
		this.stack[ index + 1 ] = value1;
				
		this.size += 2;
	}
	
	private final int fromTopIndex( final int offset ) {
		return this.size - 1 - offset;
	}
	
	private final void ensureCapacity( final int size ) {
		if ( size > this.stack.length ) {
			this.stack = Arrays.copyOf( this.stack, this.stack.length << 1 );
		}
	}
	
	private final class IteratorImpl implements Iterator< T > {
		private int index = BaseJvmStack.this.fromTopIndex( 0 );	
		
		@Override
		public final boolean hasNext() {
			return ( this.index >= 0 );
		}
		
		@Override
		public final T next() {
			try {
				return BaseJvmStack.this.stack[ this.index-- ];
			} catch ( ArrayIndexOutOfBoundsException e ) {
				NoSuchElementException ex = new NoSuchElementException();
				ex.initCause( e );
				throw ex;
			}
		}
		
		@Override
		public final void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
