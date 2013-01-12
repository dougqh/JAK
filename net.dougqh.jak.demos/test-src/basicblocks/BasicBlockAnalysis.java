package basicblocks;

import java.io.IOException;

import net.dougqh.jak.disassembler.JvmClass;
import net.dougqh.jak.disassembler.JvmMethod;
import net.dougqh.jak.disassembler.JvmReader;

public class BasicBlockAnalysis {
	public static void main(String[] args) throws IOException {
		JvmReader reader = new JvmReader(FlowControl.class.getClassLoader());
		
		JvmClass jvmClass = reader.<JvmClass>read(FlowControl.class);
		
		JvmMethod method = jvmClass.getMethod("if");
	}
}
