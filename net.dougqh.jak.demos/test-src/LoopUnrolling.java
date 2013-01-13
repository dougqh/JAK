import java.io.IOException;

import net.dougqh.jak.disassembler.JvmMethod;
import net.dougqh.jak.jvm.operations.ConstantOperation;
import net.dougqh.jak.jvm.operations.IfOperation;
import net.dougqh.jak.jvm.operations.LoadOperation;
import basicblocks.BasicBlock;
import basicblocks.FlowControl;
import basicblocks.JvmOperationMatchingIterator;
import basicblocks.Loop;
import basicblocks.Loops;

public final class LoopUnrolling {
	public static final void main(final String[] args) throws IOException {
		JvmMethod method = JvmMethod.read(FlowControl.class, "gauss");
		
		for ( Loop loop: method.get(Loops.class) ) {
			System.out.println(loop.initBlock());
			
			tryUnroll(loop);
		}
	}
	
	private static final void tryUnroll(final Loop loop) {
		if ( loop.numBlocks() != 2 ) return;
		
		BasicBlock testBlock = loop.testBlock();
		if ( testBlock.numOps() != 3 ) return;
		
		JvmOperationMatchingIterator iter = testBlock.iterator();
		
		LoadOperation loadOp = iter.nextIf(LoadOperation.class);
		if ( loadOp == null ) return; 
		
		ConstantOperation constOp = iter.nextIf(ConstantOperation.class);
		if ( constOp == null ) return;
		
		IfOperation ifOp = iter.nextIf(IfOperation.class);
		if ( ifOp == null ) return;
		
		int loopVarSlot = loadOp.slot();
		int loopLimit = constOp.<Integer>value();
		
		System.out.println("loop var: " + loopVarSlot);
		System.out.println("limit: " + loopLimit);
	}
}
