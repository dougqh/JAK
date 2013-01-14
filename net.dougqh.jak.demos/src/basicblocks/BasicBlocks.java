package basicblocks;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import net.dougqh.jak.disassembler.JvmMethod;
import net.dougqh.jak.jvm.SimpleJvmOperationProcessor;
import net.dougqh.jak.jvm.operations.BranchOperation;
import net.dougqh.jak.jvm.operations.JvmOperation;

import static basicblocks.JvmOperationMatchers.*;

/**
 * Simple basic block analysis -- current implementation is MEME blocks
 * @author dougqh
 */
public class BasicBlocks implements Iterable<BasicBlock> {
	private final NavigableMap<Integer, BasicBlock> basicBlocks;
	
	public BasicBlocks(final JvmMethod method) {
		final BlockBuilder blockBuilder = new BlockBuilder();
		blockBuilder.initFirstPass();
		
		method.process(new SimpleJvmOperationProcessor() {
			@Override
			public final boolean shouldProcess(
				final Integer pos,
				final Class<? extends JvmOperation> opClass)
			{
				// the isReturn will only really matter, when someone does if ( true ) return
				// but keep it none-the-less.
				if ( isBlockTerminating(this.lastOpClass()) ) {
					blockBuilder.markStart(pos);
				}
				
				return is(opClass, BRANCH);
			}
			
			@Override
			public final void process(final JvmOperation op) {
				BranchOperation branchOp = as(op, BRANCH);
				blockBuilder.markStart(branchOp.jump().pos());
			}
			
			private /*static*/ final boolean isBlockTerminating(final Class<? extends JvmOperation> opClass) {
				if ( opClass == null ) {
					return false;
				} else if ( is(opClass, BRANCH) ) {
					return true;
				} else if ( is(opClass, RETURN) ) {
					return true;
				} else {
					return false;
				}
			}
		});
		
		blockBuilder.initSecondPass();
		
		method.process(new SimpleJvmOperationProcessor() {
			@Override
			public final void process(final JvmOperation op) {
				BasicBlock block = blockBuilder.blockOf(op);
				block.add(op);
				
				if ( is(op, BRANCH) ) {
					BranchOperation branchOp = as(op, BRANCH);
					
					if ( branchOp.isConditional() ) {
						block.initConditionalExit(branchOp.jump().pos());
					} else {
						block.initExit(branchOp.jump().pos());
					}
				}
				
				if ( is(op, RETURN) ) {
					block.initTerminating();
				}
			}
		});
		
		this.basicBlocks = blockBuilder.basicBlocks;
	}
	
	public final BasicBlock initial() {
		return this.at(0);
	}
	
	public final BasicBlock findPrecedingBlock(final BasicBlock block) {
		return this.findPrecedingBlock(block.pos());
	}
	
	public final BasicBlock findPrecedingBlock(final int pos) {
		for ( BasicBlock block: this.basicBlocks.headMap(pos).values() ) {
			if ( block.exitsTo(pos) ) {
				return block;
			}
		}
		return null;
	}
	
	public final Collection<BasicBlock> findPriorBlocks(final BasicBlock block) {
		return this.findPriorBlocks(block.pos());
	}
	
	public final Collection<BasicBlock> findPriorBlocks(final int pos) {
		return this.basicBlocks.headMap(pos).values();
	}
	
	public final BasicBlock at(final int pos) {
		return this.basicBlocks.get(pos);
	}
	
	public final int size() {
		return this.basicBlocks.size();
	}
	
	@Override
	public final Iterator<BasicBlock> iterator() {
		return Collections.unmodifiableCollection(this.basicBlocks.values()).iterator();
	}
	
	private final class BlockBuilder {
		final NavigableMap<Integer, BasicBlock> basicBlocks = new TreeMap<Integer, BasicBlock>();
		
		private Iterator<Map.Entry<Integer, BasicBlock>> entryIterator = null;
		private Map.Entry<Integer, BasicBlock> curEntry = null;
		private Map.Entry<Integer, BasicBlock> nextEntry = null;
		
		private final void initFirstPass() {
			this.markStart(0);
		}
		
		// first pass method that tracks starting points
		private final void markStart(final int pos) {
			this.basicBlocks.put(pos, new BasicBlock(pos));
		}
		
		private final void initSecondPass() {
			this.entryIterator = this.basicBlocks.entrySet().iterator();
			
			//there will always be at least one block, so need to check hasNext
			this.nextEntry = this.entryIterator.next();
		}
		
		// second pass method that provides the block for the provided op,
		// does not add the op to the block
		private final BasicBlock blockOf(final JvmOperation op) {
			if ( this.nextEntry != null && op.pos() == this.nextEntry.getKey() ) {
				this.advance();
			}
			
			return this.curEntry.getValue();
		}
		
		private final void advance() {
			if ( this.curEntry == null ) {
				this.nextEntry.getValue().initInitial();
			} else if ( this.nextEntry != null ) {
				if ( this.curEntry.getValue().missingExitPos() ) {
					this.curEntry.getValue().initExit(this.nextEntry.getValue().pos());
				}
			}
			
			this.curEntry = this.nextEntry;
			if ( this.entryIterator.hasNext() ) {
				this.nextEntry = this.entryIterator.next();
			} else {
				this.nextEntry = null;
			}
		}
	}
}
