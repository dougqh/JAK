package basicblocks;

import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import net.dougqh.jak.disassembler.JvmMethod;
import net.dougqh.jak.jvm.SimpleJvmOperationProcessor;
import net.dougqh.jak.jvm.operations.BranchOperation;
import net.dougqh.jak.jvm.operations.JvmOperation;

public class BasicBlocks implements Iterable<BasicBlock> {
	private final Map<Integer, BasicBlock> basicBlocks;
	
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
				
				return isBranch(opClass);
			}
			
			@Override
			public final void process(final JvmOperation op) {
				BranchOperation branchOp = asBranchOp(op);
				blockBuilder.markStart(branchOp.jump().pos());
			}
			
			private /*static*/ final boolean isBlockTerminating(final Class<? extends JvmOperation> opClass) {
				if ( opClass == null ) {
					return false;
				} else if ( isBranch(opClass) ) {
					return true;
				} else if ( isReturn(opClass) ) {
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
				
				if ( isBranch(op) ) {
					block.initConditionalExit(asBranchOp(op).jump().pos());
				}
				
				if ( isReturn(op) ) {
					block.initTerminating();
				}
			}
		});
		
		this.basicBlocks = blockBuilder.basicBlocks;
	}
	
	@Override
	public final Iterator<BasicBlock> iterator() {
		return this.basicBlocks.values().iterator();
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
				this.curEntry.getValue().initExit(this.nextEntry.getValue().pos());
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
