package registers;

import java.io.IOException;
import java.lang.reflect.Type;

import net.dougqh.jak.disassembler.JvmMethod;
import net.dougqh.jak.jvm.BaseJvmLocals;
import net.dougqh.jak.jvm.BaseJvmStack;
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
		
		BaseJvmLocals<Void> locals = new BaseJvmLocals<Void>() {
			@Override
			public void load(int slot, Type type) {
				System.out.println("load " + slot + " " + type);
			}
			
			@Override
			public void store(int slot, Type type) {
				System.out.println("store " + slot + " " + type);
			}
		};
		
		BaseJvmStack<Void> stack = new BaseJvmStack<Void>() {
			@Override
			protected void stack1(Type type) {
				System.out.println("stack1 " + type);
			}
			
			@Override
			protected void unstack1(Type type) {
				System.out.println("unstack1 " + type);
			}
			
			@Override
			protected void stack2(Type type) {
				System.out.println("stack2 " + type);
			}
			
			@Override
			protected void unstack2(Type type) {
				System.out.println("unstack2" + type);
			}
		};
		
		method.process(new SimpleJvmOperationProcessor(stack, locals) {
			@Override
			public void process(JvmOperation op) {
				System.out.println(op);
			}
		});
	}
	
	public static final int demo() {
		int x = 0;
		x = x + 1;
		return x;
	}
}
