package registers;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.annotations.JvmOp;
import net.dougqh.jak.jvm.operations.JvmOperation;
import net.dougqh.jak.jvm.operations.JvmOperations;

public final class CodeGenerator {
	public static void main(String[] args) {
		printfln("public abstract class TrackingJvmOperationProcessor implements JvmOperationProcessor {");
		
		for ( Method method: JvmOperationProcessor.class.getMethods() ) {
			JvmOp opAnno = method.getAnnotation(JvmOp.class);
			if ( opAnno == null ) {
				continue;
			}
			
			JvmOperation op = JvmOperations.getPrototype(opAnno.value());
			
			printfln(1, "@Override");
			printfln(1, "public final void %s(%s) {");
			
			for ( Type type: op.getStackOperandTypes() ) {
				printfln(2, "this.stack.");
			}
			
			printfln(2, "try {");
			
			printfln(2, "} finally {");
			for ( Type type: op.getStackResultTypes() ) {
				
			}
			printfln(2, "}");
			printfln(1, "}");
		}
		
		printfln("}");
	}
	
	public static final void printfln(final String format, final Object... args) {
		printfln(0, format, args);
	}
	
	public static final void printfln(final int indent, final String format, final Object... args) {
		for ( int i = 0; i < indent; ++i ) {
			System.out.print("    ");
		}
		System.out.printf(format + "%n", args);
	}
}
