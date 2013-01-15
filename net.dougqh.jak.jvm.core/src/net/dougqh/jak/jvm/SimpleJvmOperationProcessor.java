package net.dougqh.jak.jvm;

import net.dougqh.jak.jvm.operations.JvmOperation;

public abstract class SimpleJvmOperationProcessor {
	private static final JvmStack NULL_STACK = new JvmTypeStack();
	private static final JvmLocals NULL_LOCALS = new BaseJvmLocals<Void>() {};
	
	private static final JvmOperationProcessor NULL_PROCESSOR = new BaseJvmOperationProcessor() {};
	
	private Class<? extends JvmOperation> lastOpClass;
	
	private final JvmLocals locals;
	private final JvmStack stack;
	
	public SimpleJvmOperationProcessor() {
		this(null, null);
	}
	
	public SimpleJvmOperationProcessor(final JvmStack stack) {
		this(stack, null);
	}
	
	public SimpleJvmOperationProcessor(final JvmLocals locals) {
		this(null, locals);
	}
	
	public SimpleJvmOperationProcessor(final JvmStack stack, final JvmLocals locals) {
		this.stack = stack;
		this.locals = locals;
	}
	
	public JvmLocals locals() {
		return null;
	}
	
	public JvmStack stack() {
		return null;
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
	
	public JvmOperationProcessor adapt() {
		JvmOperationProcessor processor = new RegularProcessorAdapter();
		
		if ( this.locals() != null || this.stack() != null ) {
			processor = new TrackingProcessorAdapter(processor);
		}
		
		return processor;
	}
	
	private final class TrackingProcessorAdapter extends TrackingJvmOperationProcessor {
		private final JvmOperationProcessor wrapped;
		
		public TrackingProcessorAdapter(final JvmOperationProcessor wrapped) {
			this.wrapped = wrapped;
		}
		
		@Override
		protected final JvmOperationProcessor wrapped() {
			return this.wrapped;
		}
		
		@Override
		protected final JvmLocals locals() {
			JvmLocals locals = SimpleJvmOperationProcessor.this.locals();
			if ( locals != null ) {
				return locals;
			} else {
				return NULL_LOCALS;
			}
		}
		
		@Override
		protected final JvmStack stack() {
			JvmStack stack = SimpleJvmOperationProcessor.this.stack();
			if ( stack != null ) {
				return stack;
			} else {
				return NULL_STACK;
			}
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
}
