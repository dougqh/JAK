package basicblocks;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class Loop implements Iterable<BasicBlock> {
	private final BasicBlocks allBlocks;
	private final List<BasicBlock> loopBlocks = new LinkedList<BasicBlock>();
	
	public Loop(final BasicBlocks blocks, final BasicBlock startingBlock) {
		this.allBlocks = blocks;
		this.add(startingBlock);
	}
	
	@Override
	public final Iterator<BasicBlock> iterator() {
		return Collections.unmodifiableList(this.loopBlocks).iterator();
	}
	
	public final BasicBlock at(final int pos) {
		return this.allBlocks.at(pos);
	}
	
	protected final void add(final BasicBlock block) {
		this.loopBlocks.add(block);
	}
}
