package registers;

import java.io.IOException;

import net.dougqh.jak.disassembler.JvmMethod;
import net.dougqh.jak.jvm.SimpleJvmOperationProcessor;
import net.dougqh.jak.jvm.operations.JvmOperation;


/**
 * A simple class that provides an abstract basic for converting 
 * @author dougqh
 *
 */
public final class StackTracking {
	public static void main(String[] args) throws IOException {
		JvmMethod method = JvmMethod.read(StackTracking.class, "demo");
		
		method.process(new SimpleJvmOperationProcessor() {
			@Override
			public void process(JvmOperation op) {	
			}
		});
	}
	
	public static final int demo() {
		int x = 0;
		x = x + 1;
		return x;
	}
}
