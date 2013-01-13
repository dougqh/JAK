package basicblocks;

import java.util.ArrayList;
import java.util.List;

import net.dougqh.jak.disassembler.JvmMethod;
import net.dougqh.jak.jvm.JvmOperationHydrator;
import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.operations.IfOperation;
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
		private int pos;
		
		@Override
		public final void pos(final int pos) {
			this.pos = pos;
		}
		
		@Override
		protected void add(final JvmOperation operation) {
			this.operations.add(operation);
		}
	}
}
