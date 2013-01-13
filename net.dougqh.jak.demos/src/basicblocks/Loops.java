package basicblocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import net.dougqh.jak.disassembler.JvmMethod;

/**
 * Builds on the basic block analysis - by identifying loops
 * @author dougqh
 */
public final class Loops implements Iterable<Loop> {
	private final List<Loop> loops;
	
	public Loops(final JvmMethod method) {
		//Current implementation does not attempt to account for break, continue to outer, etc.
		
		BasicBlocks blocks = method.get(BasicBlocks.class);
		
		Set<Integer> backwardTargets = new HashSet<Integer>(blocks.size());
		
		// First pass look for backwards jumps
		for ( BasicBlock block: blocks ) {
			block.addBackwardTargets(backwardTargets);
		}
		
		// Second pass - start from a backward target adding the blocks between
		// it and the destination block.
		
		this.loops = new ArrayList<Loop>(backwardTargets.size());
		
		for ( Integer backwardTarget: backwardTargets ) {
			this.loops.add(constructLoop(blocks, backwardTarget));
		}
	}
	
	public final boolean isEmpty() {
		return this.loops.isEmpty();
	}
	
	public final int size() {
		return this.loops.size();
	}
	
	@Override
	public final Iterator<Loop> iterator() {
		return Collections.unmodifiableList(this.loops).iterator();
	}
	
	private static final Loop constructLoop(final BasicBlocks blocks, final int backwardTarget) {
		BasicBlock startingBlock = blocks.at(backwardTarget);
		Loop loop = new Loop(blocks, startingBlock);
		
		//Only follow the forward path -- presumably everything else will be netted in
		PriorityQueue<Integer> targets = new PriorityQueue<Integer>(blocks.size() / 2);
		startingBlock.addForwardTargets(targets);
		
		for ( Integer pos = targets.poll(); pos != null; pos = targets.poll() ) {
			BasicBlock block = blocks.at(pos);
			loop.add(block);
			
			// If the block is the original backwards branching block, then its forward exit 
			// is out of the loop.  All other forward exits are within loop, so keep adding.
			if ( ! block.exitsTo(startingBlock) ) {
				block.addForwardTargets(targets);	
			}
		}
		
		return loop;
	}
}
