package net.dougqh.jak.jvm;

import java.lang.reflect.Type;

import net.dougqh.jak.inject.InjectionRecipient;
import net.dougqh.jak.jvm.operations.JvmOperation;

public abstract class SimpleJvmOperationProcessor implements InjectionRecipient {
	private static final JvmStackTracker NULL_STACK = new NullJvmStack();
	private static final JvmLocalsTracker NULL_LOCALS = new NullJvmLocals();
	
	private static final JvmOperationProcessor NULL_PROCESSOR = new BaseJvmOperationProcessor() {};
	
	private Class<? extends JvmOperation> lastOpClass;
	
	private final JvmLocalsTracker localsTracker;
	private final JvmStackTracker stackTracker;
	
	@Override
	public boolean shouldInject() {
		return true;
	}
	
	public SimpleJvmOperationProcessor() {
		this(null, null);
	}
	
	public SimpleJvmOperationProcessor(final JvmStackTracker stack) {
		this(stack, null);
	}
	
	public SimpleJvmOperationProcessor(final JvmLocalsTracker locals) {
		this(null, locals);
	}
	
	public SimpleJvmOperationProcessor(final JvmStackTracker stackTracker, final JvmLocalsTracker localsTracker) {
		this.stackTracker = stackTracker;
		this.localsTracker = localsTracker;
	}
	
	public JvmLocalsTracker locals() {
		// Deliberately overrideable
		return this.localsTracker;
	}
	
	public JvmStackTracker stack() {
		// Deliberately overrideable
		return this.stackTracker;
	}
	
	public final Class<? extends JvmOperation> lastOpClass() {
		return this.lastOpClass;
	}
	
	public boolean shouldProcess(
		final Integer pos,
		final Class<? extends JvmOperation> opClass)
	{
		return true;
	}
	
	public abstract void process(final JvmOperation op);
	
	public final JvmOperationProcessor adapt() {
		JvmOperationProcessor processor = new RegularProcessorAdapter();
		
		JvmLocalsTracker locals = this.locals();
		JvmStackTracker stack = this.stack();
		
		if ( locals != null || stack != null ) {
			if ( stack == null ) {
				stack = NULL_STACK;
			}
			if ( locals == null ) {
				locals = NULL_LOCALS;
			}
			
			processor = new TrackingProcessorAdapter(processor, stack, locals);
		}
		
		return processor;
	}
	
	private final class TrackingProcessorAdapter extends TrackingJvmOperationProcessor {
		private final JvmOperationProcessor wrapped;
		private final JvmStackTracker stack;
		private final JvmLocalsTracker locals;
		
		public TrackingProcessorAdapter(
			final JvmOperationProcessor wrapped,
			final JvmStackTracker stack,
			final JvmLocalsTracker locals)
		{
			this.wrapped = wrapped;
			this.stack = stack;
			this.locals = locals;
		}
		
		@Override
		protected final JvmOperationProcessor wrapped() {
			return this.wrapped;
		}
		
		@Override
		protected final JvmLocalsTracker locals() {
			return this.locals;
		}
		
		@Override
		protected final JvmStackTracker stack() {
			return this.stack;
		}
	}
	
	private final class RegularProcessorAdapter extends JvmOperationFilter {
		@Override
		public final void prepare() {}
		
		@Override
		protected JvmOperationProcessor wrapped() {
			return NULL_PROCESSOR;
		}
		
		@Override
		protected final boolean shouldFilter(final Class<? extends JvmOperation> opClass) {
			boolean result = SimpleJvmOperationProcessor.this.shouldProcess(this.pos(), opClass);
			if ( ! result ) {
				// If false there won't be a call to filter, so transition now.
				SimpleJvmOperationProcessor.this.lastOpClass = opClass;
			}
			return result;
		}
		
		@Override
		protected final void filter(final JvmOperation op) {
			SimpleJvmOperationProcessor.this.process(op);
			SimpleJvmOperationProcessor.this.lastOpClass = op.getClass();
		}
	}
	
	private static final class NullJvmStack implements JvmStackTracker {
		@Override
		public final void stack(final Type type) {
		}

		@Override
		public final void unstack(final Type type) {
		}

		@Override
		public final void pop() {
		}

		@Override
		public final void pop2() {
		}

		@Override
		public final void swap() {
		}

		@Override
		public final void dup() {
		}

		@Override
		public final void dup_x1() {
		}

		@Override
		public final void dup_x2() {
		}

		@Override
		public final void dup2() {
		}

		@Override
		public final void dup2_x1() {
		}

		@Override
		public final void dup2_x2() {
		}

		@Override
		public final void enableTypeTracking() {
		}

		@Override
		public final JvmTypeStack typeStack() {
			return null;
		}

		@Override
		public final Type topType(final Type expectedType) {
			return expectedType;
		}

		@Override
		public final int maxStack() {
			throw new UnsupportedOperationException();
		}
	
	}
	
	private static final class NullJvmLocals implements JvmLocalsTracker {
		@Override
		public final void load(final int slot, final Type type) {
		}

		@Override
		public final void store(final int slot, final Type type) {
			
		}

		@Override
		public final void inc(final int slot, final int amount) {
		}
		
		@Override
		public final Type typeOf(int slot, Type expectedType) {
			return expectedType;
		}

		@Override
		public final int declare(Type type) {
			throw new UnsupportedOperationException();
		}

		@Override
		public final void undeclare(int slot) {
			throw new UnsupportedOperationException();
		}

		@Override
		public final int maxLocals() {
			throw new UnsupportedOperationException();
		}
	}
}
