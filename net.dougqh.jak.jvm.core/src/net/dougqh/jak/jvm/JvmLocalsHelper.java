package net.dougqh.jak.jvm;

import java.util.Arrays;

public final class JvmLocalsHelper<T> {
	private static final Cat2 CAT2_MARKER = new Cat2();
	
	private Object[] locals;
	
	public JvmLocalsHelper() {
		this(8);
	}
	
	public JvmLocalsHelper(final int initialCapacity) {
		this.locals = new Object[initialCapacity];
	}
	
	public final void set(final int index, final T value, final Category category) {
		// avoid crazy switch dispatch
		if ( category.ordinal() == 0 ) {
			this.set(index, value);
		} else {
			this.set2(index, value);
		}
	}
	
	public final void set(final int index, final T value) {
		this.setImpl(index, value);
	}
	
	public final void set2(final int index, final T value) {
		this.setImpl(index, value);
		this.setImpl(index, CAT2_MARKER);
	}
	
	public final T get(final int index, final Category category) {
		// avoid crazy switch dispatch
		if ( category.ordinal() == 0 ) {
			return this.get(index);
		} else {
			return this.get2(index);
		}
	}
	
	public final T get(final int index) {
		return this.<T>getImpl(index);
	}
	
	public final T get2(final int index) {
		return this.<T>getImpl(index);
	}
	
	private final void setImpl(final int index, final Object value) {
		if ( index >= this.locals.length ) {
			this.locals = Arrays.copyOf(this.locals, this.locals.length << 1);
		}
		this.locals[index] = value;
	}
	
	private final <U> U getImpl(final int index) {
		if ( index < this.locals.length ) {
			@SuppressWarnings("unchecked")
			U casted = (U)this.locals[index];
			return casted;
		} else {
			return null;
		}
	}
	
	private static final class Cat2 {}
}
