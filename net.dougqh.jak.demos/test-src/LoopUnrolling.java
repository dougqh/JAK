import java.io.IOException;

import net.dougqh.jak.disassembler.JvmMethod;
import basicblocks.BasicBlock;
import basicblocks.FlowControl;
import basicblocks.Loop;
import basicblocks.Loops;

public final class LoopUnrolling {
	public static final void main(final String[] args) throws IOException {
		JvmMethod method = JvmMethod.read(FlowControl.class, "gauss");
		
		for ( Loop loop: method.get(Loops.class) ) {
			for ( BasicBlock block: loop ) {
				System.out.println(block);
			}
		}
	}
}
