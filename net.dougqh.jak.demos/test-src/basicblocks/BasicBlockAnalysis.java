package basicblocks;

import java.io.IOException;

import net.dougqh.jak.disassembler.JvmClass;
import net.dougqh.jak.disassembler.JvmMethod;
import net.dougqh.jak.disassembler.JvmReader;
import net.dougqh.jak.jvm.operations.JvmOperation;

public class BasicBlockAnalysis {
	public static void main(String[] args) throws IOException {
		JvmReader reader = new JvmReader(FlowControl.class.getClassLoader());
		
		JvmClass jvmClass = reader.<JvmClass>read(FlowControl.class);
		
		System.out.println(jvmClass.getMethods());
		
		JvmMethod method = jvmClass.getMethod("signum");
		System.out.println(method);
		
		for ( JvmOperation op: method.getOperations() ) {
			System.out.println(op);
		}
	}
}
