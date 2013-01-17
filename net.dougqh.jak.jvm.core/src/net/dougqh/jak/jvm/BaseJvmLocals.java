package net.dougqh.jak.jvm;

import java.lang.reflect.Type;
import java.util.Arrays;

@Deprecated
public abstract class BaseJvmLocals<T> implements JvmLocalsTracker {
	private T[] locals;
	
	protected BaseJvmLocals() {
		this(8);
	}
	
	@SuppressWarnings( "unchecked" )
	protected BaseJvmLocals( final int initialCapacity ) {
		this.locals = (T[])new Object[ initialCapacity ];
	}
	
	@Override
	public abstract void load(int slot, Type type);
	
	@Override
	public abstract void store(int slot, Type type);
	
	public T set(final int slot, final T value) {
		if ( slot > this.locals.length ) {
			this.locals = Arrays.copyOf(this.locals, this.locals.length << 1);
		}
		T oldValue = this.locals[slot];
		this.locals[slot] = value;
		return oldValue;
	}
	
	public T get(final int slot) {
		if ( slot < this.locals.length ) {
			return this.locals[slot];
		} else {
			return null;
		}
	}
	
	// The deprecated methods
	@Override
	public int declare(final Type type) {
		return -1;
	}
	
	@Override
	public void undeclare(final int slot) {
	}
	
	@Override
	public void inc(final int slot, final int amount) {
	}
	
	@Override
	public Type typeOf(final int slot, final Type expectedType) {
		return expectedType;
	}
	
	@Override
	public int maxLocals() {
		throw new UnsupportedOperationException();
	}
}
