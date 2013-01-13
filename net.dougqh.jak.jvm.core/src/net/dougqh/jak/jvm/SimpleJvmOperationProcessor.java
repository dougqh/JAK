package net.dougqh.jak.jvm;

import net.dougqh.jak.jvm.operations.BranchOperation;
import net.dougqh.jak.jvm.operations.JvmOperation;
import net.dougqh.jak.jvm.operations.ReturnOperation;

public abstract class SimpleJvmOperationProcessor {
	private Class<? extends JvmOperation> lastOpClass;
	
	public Class<? extends JvmOperation> lastOpClass() {
		return this.lastOpClass;
	}
	
	public boolean shouldProcess(
		final Integer pos,
		final Class<? extends JvmOperation> opClass)
	{
		return true;
	}
	
	public abstract void process(final JvmOperation op);
	
	public static final boolean isBranch(final Class<? extends JvmOperation> opClass) {
		return BranchOperation.class.isAssignableFrom(opClass);
	}
	
	public static final boolean isBranch(final JvmOperation op) {
		return (op instanceof BranchOperation);
	}
	
	public static final BranchOperation asBranchOp(final JvmOperation op) {
		return (BranchOperation)op;
	}
	
	public static final boolean isReturn(final Class<? extends JvmOperation> opClass) {
		return ReturnOperation.class.isAssignableFrom(opClass);
	}
	
	public static final boolean isReturn(final JvmOperation op) {
		return (op instanceof ReturnOperation);
	}
	
	public JvmOperationProcessor adapt() {
		return new RegularProcessorAdapter(this);
	}
	
	private static final class RegularProcessorAdapter extends JvmOperationFilter {
		private static final JvmOperationProcessor NULL_PROCESSOR = new BaseJvmOperationProcessor();
		
		private final SimpleJvmOperationProcessor simpleProcessor;
		
		public RegularProcessorAdapter(final SimpleJvmOperationProcessor simpleProcessor) {
			this.simpleProcessor = simpleProcessor;
		}
		
		@Override
		public final void prepare() {}
		
		@Override
		protected JvmOperationProcessor wrapped() {
			return NULL_PROCESSOR;
		}
		
		@Override
		protected final boolean shouldFilter(final Class<? extends JvmOperation> opClass) {
			boolean result = this.simpleProcessor.shouldProcess(this.pos(), opClass);
			if ( ! result ) {
				// If false there won't be a call to filter, so transition now.
				this.simpleProcessor.lastOpClass = opClass;
			}
			return result;
		}
		
		@Override
		protected final void filter(final JvmOperation op) {
			this.simpleProcessor.process(op);
			this.simpleProcessor.lastOpClass = op.getClass();
		}
	}
}
