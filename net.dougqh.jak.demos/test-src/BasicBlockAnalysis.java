

import java.io.IOException;

import net.dougqh.jak.disassembler.JvmClass;
import net.dougqh.jak.disassembler.JvmMethod;
import net.dougqh.jak.disassembler.JvmReader;
import net.dougqh.jak.jvm.operations.JvmOperation;
import basicblocks.BasicBlock;
import basicblocks.BasicBlocks;
import basicblocks.FlowControl;

public class BasicBlockAnalysis {
	public static void main(String[] args) throws IOException {
		JvmReader reader = new JvmReader(FlowControl.class.getClassLoader());
		
		JvmClass jvmClass = reader.<JvmClass>read(FlowControl.class);
		printBlocksFor(jvmClass, "signum");
		System.out.println();
		printBlocksFor(jvmClass, "sum");
	}
	
	private static final void printBlocksFor(final JvmClass jvmClass, final String methodName) {
		System.out.println(methodName);
		
		JvmMethod method = jvmClass.getMethod(methodName);
		for ( JvmOperation op: method.operations() ) {
			System.out.println(op.pos() + " " + op);
		}
		
		BasicBlocks basicBlocks = method.get(BasicBlocks.class);
		for ( BasicBlock basicBlock: basicBlocks ) {
			System.out.println(basicBlock);
			System.out.println();
		}
	}
}
