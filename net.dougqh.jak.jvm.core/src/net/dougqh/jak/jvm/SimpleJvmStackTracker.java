package net.dougqh.jak.jvm;

import java.lang.reflect.Type;

import net.dougqh.jak.types.Types;

public abstract class SimpleJvmStackTracker<T> implements JvmStackTracker {
	public final JvmStackHelper<T> stack;
	
	public SimpleJvmStackTracker() {
		this(new JvmStackHelper<T>(8));
	}

	public SimpleJvmStackTracker(final JvmStackHelper<T> stack) {
		this.stack = stack;
	}	
	@Override
	public final void stack(final Type type) {
		if ( Types.isCategory1(type) ) {
			this.stack(type, Category.CAT1);
		} else {
			this.stack(type, Category.CAT2);
		}
	}
	
	protected abstract void stack(final Type type, final Category category);
	
	@Override
	public final void unstack(final Type type) {
		if ( Types.isCategory1(type) ) {
			this.unstack(type, Category.CAT1);
		} else {
			this.unstack(type, Category.CAT2);
		}
	}
	
	protected void unstack(final Type type, final Category category) {
		this.stack.pop(category);
	}
	
	@Override
	public Type topType(final Type expectedType) {
		return expectedType;
	}
	
	@Override
	public void dup() {
		this.stack.dup();
	}
	
	@Override
	public void dup_x1() {
		this.stack.dup_x1();
	}
	
	@Override
	public void dup2() {
		this.stack.dup2();
	}
	
	@Override
	public void dup_x2() {
		this.stack.dup_x2();
	}
	
	@Override
	public void dup2_x1() {
		this.stack.dup2_x1();
	}
	
	public void dup2_x2() {
		this.stack.dup_x2();
	}
	
	@Override
	public void pop() {
		this.stack.pop();
	}
	
	@Override
	public void pop2() {
		this.stack.pop2();
	}
	
	@Override
	public void swap() {
		this.stack.swap();
	}
	
	// The deprecated and hopefully soon to be removed methods
	@Override
	public final void enableTypeTracking() {
		//pass
	}
	
	@Override
	public final int maxStack() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final JvmTypeStack typeStack() {
		return null;
	}
}
