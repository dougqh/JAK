package net.dougqh.jak.jvm;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import net.dougqh.jak.types.Types;

//TODO: This is a mess -- desperately needs to be cleaned up
public abstract class BaseJvmStack< T > implements JvmStack, Iterable< T > {
	private static final boolean CAT1 = true;
	private static final boolean CAT2 = false;
	
	private Holder<T>[] stack;
	private int size = 0;
	
	protected BaseJvmStack() {
		this(8);
	}
	
	@SuppressWarnings("unchecked")
	protected BaseJvmStack( final int initialCapacity ) {
		this.stack = (Holder<T>[])new Holder[ initialCapacity ];
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
	public final void stack(final Type type) {
		if ( Types.isCategory1(type) ) {
			this.stack1(type);
		} else {
			this.stack2(type);
		}
	}
	
	protected abstract void stack1(final Type type);
	
	protected abstract void stack2(final Type type);
	
	@Override
	public final void unstack(final Type type) {
		if ( Types.isCategory1(type) ) {
			this.unstack1(type);
		} else {
			this.unstack2(type);
		}
	}
	
	protected abstract void unstack1(final Type type);
	
	protected abstract void unstack2(final Type type);
	
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
		return this.getFromTop( 0 ).value;
	}
	
	public final void push1( final T value ) {
		this.pushImpl( new Holder<T>(value, CAT1) );
	}
	
	public final void push2( final T value ) {
		this.pushImpl( new Holder<T>(value, CAT2) );
	}
	
	public final void pop1( final T value ) {
		this.popImpl();
	}
	
	// Difference between the two pop2 methods is confusing and should be resolved
	public final void pop2( final T value ) {
		this.popImpl();
	}
	
	public final void pop() {
		this.popImpl();
	}
	
	public final void pop2() {
		Holder<T> holder = this.popImpl();
		if ( holder.isCat1 ) {
			this.popImpl();
		}
	}
	
	public final void swap() {
		Holder<T> temp = this.getFromTop( 0 );
		this.setFromTop( 0, this.getFromTop( 1 ) );
		this.setFromTop( 1, temp );
	}
	
	public final void dup() {
		Holder<T> top = this.getFromTop( 0 );
		this.pushImpl( top );
	}
	
	public final void dup_x1() {
		Holder<T> top = this.getFromTop( 0 );
		this.insertFromTop( 1, top );
	}
	
	public final void dup_x2() {
		Holder<T> top = this.getFromTop( 0 );
		Holder<T> nextToTop = this.getFromTop( 1 );
		
		if ( nextToTop.isCat1 ) {
			this.insertFromTop( 2, top );
		} else {
			this.insertFromTop( 1, top );
		}
	}
	
	public final void dup2() {
		Holder<T> top = this.getFromTop( 0 );
		if ( top.isCat1 ) {
			Holder<T> nextToTop = this.getFromTop( 1 );
			this.pushImpl( nextToTop );
			this.pushImpl( top );
		} else {
			this.pushImpl( top );
		}
	}
	
	public final void dup2_x1() {
		Holder<T> top = this.getFromTop( 0 );
		if ( top.isCat1 ) {
			Holder<T> nextToTop = this.getFromTop( 1 );
			this.insertPairFromTop( 2, top, nextToTop );
		} else {
			this.insertFromTop( 1, top );
		}
	}
	
	public final void dup2_x2() {
		Holder<T> top = this.getFromTop( 0 );
		if ( top.isCat1 ) {
			Holder<T> firstFromTop = this.getFromTop( 1 );
			
			Holder<T> secondFromTop = this.getFromTop( 2 );
			if ( secondFromTop.isCat1 ) {
				this.insertPairFromTop( 3, top, firstFromTop );
			} else {
				this.insertPairFromTop( 2, top, firstFromTop );
			}
		} else {
			Holder<T> firstFromTop = this.getFromTop( 1 );
			if ( firstFromTop.isCat1 ) {
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
	
	private final void pushImpl( final Holder<T> holder) {
		this.ensureCapacity( this.size + 1 );
		
		this.stack[ this.size++ ] = holder;
	}
	
	private final Holder<T> popImpl() {
		return this.stack[ --this.size ];
	}
	
	private final Holder<T> getFromTop( final int offset ) {
		return this.stack[ this.fromTopIndex( offset ) ];
	}
	
	private final void setFromTop( final int offset, final Holder<T> holder ) {
		this.stack[ this.fromTopIndex( offset ) ] = holder;
	}
	
	private final void insertFromTop( final int offset, final Holder<T> holder ) {
		this.ensureCapacity( this.size + 1 );
		
		int index = this.fromTopIndex( offset );
		int length = this.size - index;
		System.arraycopy(
			this.stack, index,
			this.stack, index + 1,
			length );
		this.stack[ index ] = holder;
		
		++this.size;
	}
	
	private final void insertPairFromTop( final int offset, final Holder<T> holder1, final Holder<T> holder2 ) {
		this.ensureCapacity( this.size + 2 );
		
		int index = this.fromTopIndex( offset );
		int length = this.size - index;
		System.arraycopy(
			this.stack, index,
			this.stack, index + 2,
			length );
		this.stack[ index ] = holder2;
		this.stack[ index + 1 ] = holder1;
				
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
				return BaseJvmStack.this.stack[ this.index-- ].value;
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
	
	private static final class Holder<T> {
		final T value;
		final boolean isCat1;
		
		Holder(final T value, final boolean isCat1) {
			this.value = value;
			this.isCat1 = isCat1;
		}
	}
}
