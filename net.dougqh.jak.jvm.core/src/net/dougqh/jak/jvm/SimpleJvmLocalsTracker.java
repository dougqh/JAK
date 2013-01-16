package net.dougqh.jak.jvm;

import java.lang.reflect.Type;

import net.dougqh.jak.types.Types;

public abstract class SimpleJvmLocalsTracker<T> implements JvmLocals {
	public final JvmLocalsHelper<T> locals;
	
	public SimpleJvmLocalsTracker() {
		this(new JvmLocalsHelper<T>(8));
	}
	
	public SimpleJvmLocalsTracker(final JvmLocalsHelper<T> locals) {
		this.locals = locals;
	}
	
	@Override
	public final void load(final int slot, final Type type) {
		if ( Types.isCategory1(type) ) {
			this.load(slot, type, Category.CAT1);
		} else {
			this.load(slot, type, Category.CAT2);
		}
	}
	
	protected void load(final int slot, final Type type, final Category category) {
	}
	
	@Override
	public final void store(final int slot, final Type type) {
		if ( Types.isCategory1(type) ) {
			this.store(slot, type, Category.CAT1);
		} else {
			this.store(slot, type, Category.CAT2);
		}
	}
	
	protected abstract void store(final int slot, final Type type, final Category category);
	
	@Override
	public void inc(final int slot, final int amount) {
		this.load(slot, int.class, Category.CAT1);
		this.store(slot, int.class, Category.CAT1);
	}
	
	@Override
	public Type typeOf(final int slot, final Type expectedType) {
		return expectedType;
	}
	
	@Override
	public final int declare(Type type) {
		throw new UnsupportedOperationException();
	}
	
	
	@Override
	public final void undeclare(final int slot) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final int maxLocals() {
		throw new UnsupportedOperationException();
	}
}
