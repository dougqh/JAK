package net.dougqh.jak.jvm;

import java.util.Arrays;

public final class JvmStackHelper<T> {
	private static final Cat2 CAT2_MARKER = new Cat2();
	
	private Object[] stack = null;
	private int size = 0;
	
	public JvmStackHelper() {
		this(8);
	}
	
	public JvmStackHelper(final int initialCapacity) {
		this.stack = new Object[initialCapacity];
	}
	
	public final int size() {
		return this.size;
	}
	
	public final boolean isEmpty() {
		return ( this.size == 0 );
	}
	
	public final int push(final T value, final Category category) {
		// avoid crazy switch dispatch
		if ( category.ordinal() == 0 ) {
			return this.push(value);
		} else {
			return this.push2(value);
		}
	}
	
	public final int push(final T value) {
		return this.pushImpl(value);
	}
	
	public final int push2(final T value) {
		int result = this.pushMarker();
		this.pushImpl(value);
		return result;
	}
	
	public final T peek() {
		return this.peekImpl();
	}
	
	public final T pop(final Category category) {
		// avoid crazy switch dispatch
		if ( category.ordinal() == 0 ) {
			return this.pop();
		} else {
			return this.pop2();
		}
	}
	
	public final T pop() {
		return this.popImpl();
	}
	
	public final T pop2() {
		T value = this.pop();
		this.pop();
		return value;
	}
	
	public final void dup() {
		this.dup_x(0);
	}
	
	public final void dup_x1() {
		this.dup_x(1);
	}
	
	public final void dup_x2() {
		this.dup_x(2);
	}
	
	private final void dup_x(final int offset) {
		this.pushImpl(this.stack[this.size - 1 - offset]);
	}
	
	public final void swap() {
		Object top = this.stack[this.size - 1];
		this.stack[this.size - 1] = this.stack[this.size - 2];
		this.stack[this.size - 2] = top;
	}
	
	public final void dup2() {
		this.dup2_x(0);
	}
	
	public final void dup2_x1() {
		this.dup2_x(1);
	}
	
	public final void dup2_x2(final int offset) {
		this.dup2_x(2);
	}
	
	private final void dup2_x(final int offset) {
		Object topOfPair = this.stack[this.size - 2 - offset];
		Object bottomOfPair = this.stack[this.size - 1 - offset];
		this.pushImpl(bottomOfPair);
		this.pushImpl(topOfPair);
	}
	
	private final int pushMarker() {
		return this.pushImpl(CAT2_MARKER);
	}
	
	private final T peekImpl() {
		@SuppressWarnings("unchecked")
		T casted = (T)this.stack[this.size - 1];
		return casted;
	}
	
	private final int pushImpl(final Object value) {
		int offset = this.size;
		
		if ( offset >= this.stack.length ) {
			this.stack = Arrays.copyOf(this.stack, this.stack.length << 1);
		}
		this.stack[offset] = value;
		++this.size;
		
		return offset;
	}
	
	private final T popImpl() {
		@SuppressWarnings("unchecked")
		T casted = (T)this.stack[this.size - 1];
		
		// null out so we don't hang onto what should be garbage
		this.stack[this.size - 1] = null;
		--this.size;
		
		return casted;
	}
	
	private static final class Cat2 {} 
}
