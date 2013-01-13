package basicblocks;

import net.dougqh.jak.disassembler.JvmMethod;
import net.dougqh.jak.jvm.JvmOperationHydrator;
import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.operations.JvmOperation;

public class BasicBlocks {
	public BasicBlocks(final JvmMethod method) {
		OpProcessor opProcessor = new OpProcessor();
		method.processOperations(opProcessor);
	}
	
	private static final class OpProcessor
		extends JvmOperationHydrator
		implements JvmOperationProcessor.PositionAware
	{
		@Override
		protected final void process(final JvmOperation operation) {
			
		}
	}
}
