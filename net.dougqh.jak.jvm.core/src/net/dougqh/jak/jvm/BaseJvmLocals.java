package net.dougqh.jak.jvm;

import java.lang.reflect.Type;
import java.util.Arrays;

public abstract class BaseJvmLocals<T> implements JvmLocals {
	private T[] locals;
	
	protected BaseJvmLocals() {
		this(8);
	}
	
	@SuppressWarnings( "unchecked" )
	protected BaseJvmLocals( final int initialCapacity ) {
		this.locals = (T[])new Object[ initialCapacity ];
	}
	
	@Override
	public void load(int slot, Type type) {
		T value = this.fromType(slot, type);
		if ( value != null ) {
			this.set(slot, value);
		}
	}
	
	@Override
	public void store(int slot, Type type) {
		T value = this.fromType(slot, type);
		if ( value != null ) {
			this.set(slot, value);
		}
	}
	
	public T fromType(final int slot, final Type type) {
		return null;
	}
	
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
	public int declare(Type type) {
		return -1;
	}
	
	@Override
	public void undeclare(int slot) {
	}
	
	@Override
	public void inc(int slot) {
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
