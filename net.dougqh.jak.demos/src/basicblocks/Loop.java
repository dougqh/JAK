package basicblocks;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public final class Loop implements Iterable<BasicBlock> {
	private final BasicBlocks allBlocks;
	
	private final BasicBlock startingBlock;
	private BasicBlock endingBlock;
	private BasicBlock initBlock;
	private final LinkedList<BasicBlock> loopBlocks = new LinkedList<BasicBlock>();
	
	public Loop(final BasicBlocks blocks, final BasicBlock startingBlock) {
		this.allBlocks = blocks;
		
		this.startingBlock = startingBlock;
		this.add(startingBlock, false);
	}
	
	public final BasicBlock initBlock() {
		return this.initBlock;
	}
	
	public final BasicBlock startingBlock() {
		return this.loopBlocks.getFirst();
	}
	
	public final BasicBlock endingBlock() {
		return this.endingBlock;
	}
	
	public final BasicBlock testBlock() {
		if ( this.endingBlock.conditionallyExits() ) {
			return this.endingBlock;
		} else {
			throw new IllegalStateException();
		}
	}
	
	public final int numBlocks() {
		return this.loopBlocks.size();
	}
	
	@Override
	public final Iterator<BasicBlock> iterator() {
		return Collections.unmodifiableList(this.loopBlocks).iterator();
	}
	
	public final BasicBlock at(final int pos) {
		return this.allBlocks.at(pos);
	}
	
	protected final void add(final BasicBlock block, final boolean isEnd) {
		this.loopBlocks.add(block);
		if ( isEnd ) {
			if ( this.endingBlock != null ) {
				throw new IllegalStateException();
			}
			this.endingBlock = block;
			this.initBlock = this.computeInitialBlock();
		}
	}
	
	private final BasicBlock computeInitialBlock() {
		// TODO: REVIST ME, PLEASE!
		// This implementation is rather ugly
		// Looks for a prior block that either leads into the startBlock or paradoxically the endingBlock
		// The latter test is a bit of hack to work with for loops compiled by Eclipse.
		// This should definitely be revisited.
		for ( BasicBlock priorBlock: this.allBlocks.findPriorBlocks(this.startingBlock) ) {
			if ( priorBlock.unconditionallyExitsTo(this.startingBlock) ) {
				return priorBlock;
			}
			if ( priorBlock.unconditionallyExitsTo(this.endingBlock) ) {
				return priorBlock;
			}
		}
		return null;
	}
}
