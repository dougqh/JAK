

import java.io.IOException;

import net.dougqh.jak.disassembler.JvmClass;
import net.dougqh.jak.disassembler.JvmMethod;
import basicblocks.BasicBlock;
import basicblocks.BasicBlocks;
import basicblocks.FlowControl;

public class BasicBlockAnalysis {
	public static void main(String[] args) throws IOException {
		JvmClass jvmClass = JvmClass.read(FlowControl.class);
		printBlocksFor(jvmClass, "signum");
		System.out.println();
		printBlocksFor(jvmClass, "sum");
	}
	
	private static final void printBlocksFor(final JvmClass jvmClass, final String methodName) {
		System.out.println(methodName);
		
		JvmMethod method = jvmClass.getMethod(methodName);
		for ( BasicBlock basicBlock: method.get(BasicBlocks.class) ) {
			System.out.println(basicBlock);
			System.out.println();
		}
	}
}
