import java.io.IOException;

import net.dougqh.jak.disassembler.JvmMethod;
import net.dougqh.jak.jvm.operations.ConstantOperation;
import net.dougqh.jak.jvm.operations.IfOperation;
import net.dougqh.jak.jvm.operations.LoadOperation;
import net.dougqh.jak.jvm.operations.StoreOperation;
import basicblocks.BasicBlock;
import basicblocks.FlowControl;
import basicblocks.JvmOperationMatcher;
import basicblocks.JvmOperationMatchingIterator;
import basicblocks.Loop;
import basicblocks.Loops;
import static basicblocks.JvmOperationMatchers.*;

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
		
		
		// First analyze the test block to see if it has the relevant bits
		BasicBlock testBlock = loop.testBlock();
		if ( testBlock.numOps() != 3 ) return;
		
		JvmOperationMatchingIterator testIter = testBlock.iterator();
		
		LoadOperation loadOp = testIter.nextIf(LOAD);
		if ( loadOp == null ) return; 
		
		ConstantOperation limitConstOp = testIter.nextIf(ConstantOperation.class);
		if ( limitConstOp == null ) return;
		
		IfOperation ifOp = testIter.nextIf(IfOperation.class);
		if ( ifOp == null ) return;
		
		
		int loopVarSlot = loadOp.slot();
		
		
		// Second analyze the initialization block
		JvmOperationMatchingIterator initIter = loop.initBlock().iterator();
		
		StoreOperation storeOp = initIter.findNext(store(loopVarSlot));
		if ( storeOp == null ) return;
		
		ConstantOperation initConstOp = initIter.priorIf(CONST);
		if ( initConstOp == null ) return;
		
		
		int loopInit = initConstOp.<Integer>value();
		int loopLimit = limitConstOp.<Integer>value();
		
		System.out.println("loop var: " + loopVarSlot);
		System.out.println("init: " + loopInit);
		System.out.println("limit: " + loopLimit);
	}
}
