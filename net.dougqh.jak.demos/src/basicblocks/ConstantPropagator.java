package basicblocks;

import net.dougqh.jak.jvm.JvmMethodStats;
import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.SimpleJvmOperationProcessor;
import net.dougqh.jak.jvm.operations.JvmOperation;
import net.dougqh.jak.jvm.operations.iinc;
import static basicblocks.JvmOperationMatchers.*;

public final class ConstantPropagator {
	private final Value[] stack;
	private final Value[] locals;
	
	public ConstantPropagator(final JvmMethodStats methodStats) {
		this.stack = new Value[methodStats.maxStack()];
		this.locals = new Value[methodStats.maxLocals()];
	}
	
	public final JvmOperationProcessor asProcessor() {
		
	}
	
	private static final class Value {
		private boolean isConst = false;
		private Object constValue = null;
		
		public final void unknown() {
			this.isConst = false;
		}
		
		public final void const_(final Object value) {
			this.isConst = true;
			this.constValue = value;
		}
	}
	
	private static final class ProcessorImpl extends SimpleJvmOperationProcessor {
		@Override
		public final boolean shouldProcess(
			final Integer pos,
			final Class<? extends JvmOperation> opClass)
		{
			//Eventually, this needs to takes the stack manipulation operations into account
			return is(opClass, CONST) || is(opClass, STORE) || is(opClass, LOAD) || is(opClass, iinc.class);
		}
		
		@Override
		public final void process(final JvmOperation op) {
			
		}
	}
}
