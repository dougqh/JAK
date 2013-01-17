package registers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;

import javax.inject.Inject;

import net.dougqh.jak.disassembler.JvmMethod;
import net.dougqh.jak.jvm.Category;
import net.dougqh.jak.jvm.JvmLocalsHelper;
import net.dougqh.jak.jvm.JvmLocalsTracker;
import net.dougqh.jak.jvm.JvmStackHelper;
import net.dougqh.jak.jvm.JvmStackTracker;
import net.dougqh.jak.jvm.SimpleJvmLocalsTracker;
import net.dougqh.jak.jvm.SimpleJvmOperationProcessor;
import net.dougqh.jak.jvm.SimpleJvmStackTracker;
import net.dougqh.jak.jvm.operations.BinaryOperation;
import net.dougqh.jak.jvm.operations.ConstantOperation;
import net.dougqh.jak.jvm.operations.JvmOperation;
import net.dougqh.jak.jvm.operations.UnaryOperation;
import net.dougqh.jak.jvm.operations.iinc;
import basicblocks.BasicBlocks;
import static basicblocks.JvmOperationMatchers.*;

/**
 * A simple class that tracks how constants and results move the between
 * the stack and local variables.
 * @author dougqh
 */
public final class DataFlowAnalysis {
	public static void main(String[] args) throws IOException {
		JvmMethod method = JvmMethod.read(DataFlowAnalysis.class, "demo");
		
		DataFlowProcessor processor = new DataFlowProcessor();
		method.process(processor);
		System.out.println(processor.basicBlocks);
	}
	
	public static final int demo() {
		int x = 2;
		int y = 3;
		return x + y;
	}
	
	public static final class DataFlowProcessor extends SimpleJvmOperationProcessor {
		private final JvmStackHelper<Record> stack = new JvmStackHelper<Record>();
		private final JvmLocalsHelper<Record> locals = new JvmLocalsHelper<Record>();
		
		private Record toStack = null;
		private final Record[] fromStack = new Record[2];
		
		@Inject
		public BasicBlocks basicBlocks;
		
		@Override
		public final JvmStackTracker stack() {
			return new SimpleJvmStackTracker<Record>(this.stack) {
				@Override
				protected final void stack(final Type type, final Category category) {
					this.stack.push(DataFlowProcessor.this.toStack(), category);
				}
				
				@Override
				protected final void unstack(final Type type, final Category category) {
					DataFlowProcessor.this.fromStack(this.stack.pop(category));
				}
			};
		}
		
		@Override
		public final JvmLocalsTracker locals() {
			return new SimpleJvmLocalsTracker<Record>(this.locals) {
				@Override
				protected void load(final int slot, final Type type, final Category category) {
					//needs to produce a nonce if null for parameter handling
					Record record = this.locals.get(slot, category);
					System.out.printf("load %s%n", record);
					
					DataFlowProcessor.this.toStack(record);
				}
				
				@Override
				protected final void store(final int slot, final Type type, final Category category) {
					Record record = DataFlowProcessor.this.fromStack();
					System.out.printf("store %s%n", record);
					
					this.locals.set(slot, record, category);
				}
				
				@Override
				public final void inc(final int slot, final int amount) {
					Record record = this.locals.get(slot);
					Record newRecord = record.inc(amount);
					
					this.locals.set(slot, record.inc(amount));
					
					System.out.print("inc ");
					System.out.print(record);
					System.out.print("->");
					System.out.print(newRecord);
					System.out.println();
				}
			};
		}
		
		private static final boolean isConst(final Record record) {
			return ( record instanceof ConstRecord );
		}
		
		private final void toStack(final Record record) {
			//System.out.printf("toStack %s%n", record);
			
			this.toStack = record;
		}
		
		private final Record toStack() {
			Record forStack = this.toStack;
			this.toStack = null;
			return forStack;
		}
		
		private final void fromStack(final Record record) {
			//System.out.printf("fromStack %s%n", record);
			
			if ( this.fromStack[0] == null ) {
				this.fromStack[0] = record;
			} else {
				this.fromStack[1] = record;
			}
		}
		
		private final <R extends Record> R fromStack() {
			if ( this.fromStack[1] != null ) {
				@SuppressWarnings("unchecked")
				R result = (R)this.fromStack[1];
				this.fromStack[1] = null;
				return result;
			} else {
				@SuppressWarnings("unchecked")
				R result = (R)this.fromStack[0];
				this.fromStack[0] = null;
				return result;
			}
		}
		
		private final boolean canFold() {
			if ( this.fromStack[0] == null ) {
				//stack is empty
				return false;
			}
			if ( ! isConst( this.fromStack[0] ) ) {
				return false;
			}
			if ( this.fromStack[1] != null && ! isConst(this.fromStack[1]) ) {
				return false;
			}
			return true;
		}
		
		private final Record[] allFromStack() {
			Record[] result;
			if ( this.fromStack[1] != null ) {
				result = Arrays.copyOf(this.fromStack, 2);
			} else {
				result = Arrays.copyOf(this.fromStack, 1);
			}
			this.fromStack[0] = null;
			this.fromStack[1] = null;
			return result;
		}
		
		@Override
		public final boolean shouldProcess(
			final Integer pos,
			final Class<? extends JvmOperation> opClass )
		{
			if ( is(opClass, CONST) ) {
				// const is hydrated, so that the value can be captured
				return true;
			} else if ( is(opClass, LOAD) || is(opClass, STORE) || is(opClass, iinc.class ) ) {
				// load, stores, and iincs are handled via locals tracker
				return false;
			} else if ( is(opClass, RETURN) ) {
				System.out.println(new ComputeRecord(opClass, this.allFromStack()));
				return false;
			} else if ( is(opClass, UNARY) && canFold() ) {
				// constant fold, but hydrate the operation to do it
				return true;
			} else if ( is(opClass, BINARY) && canFold() ) {
				//constant fold, but hydrate the operation to do it
				return true;
			} else {
				// everything else, create a nonce that represents the instruction being performed
				this.toStack(new ComputeRecord(opClass, this.allFromStack()));
				return false;
			}
		}
		
		@Override
		public final void process(final JvmOperation op) {
			if ( is(op, CONST) ) {
				ConstantOperation constOp = as(op, CONST);
				this.toStack(new ConstRecord(constOp.value()));
			} else if ( is(op, UNARY) ) {
				UnaryOperation unaryOp = as(op, UNARY);
				
				ConstRecord input = this.<ConstRecord>fromStack();
				Object output = unaryOp.fold(input.value);
				
				this.toStack(new ConstRecord(output));
			} else if ( is(op, BINARY) ) {
				BinaryOperation binaryOp = as(op, BINARY);
				
				ConstRecord rhsInput = this.<ConstRecord>fromStack();
				ConstRecord lhsInput = this.<ConstRecord>fromStack();
				Object output = binaryOp.fold(lhsInput.value, rhsInput.value);
				
				this.toStack(new ConstRecord(output));
			}
		}
	}
	
	static abstract class Record {
		protected abstract Record inc(final int amount);
	}
	
	static final class ConstRecord extends Record {
		final Object value;
		
		ConstRecord(final Object value) {
			this.value = value;
		}
		
		@Override
		protected final Record inc(final int amount) {
			//Only valid for integers
			Integer value = (Integer)this.value;
			return new ConstRecord(value + amount);
		}
		
		@Override
		public final String toString() {
			return "<const " + this.value + ">";
		}
	}
	
	static final class ComputeRecord extends Record {
		final Class<? extends JvmOperation> opClass;
		final Record[] inputs;
		
		public ComputeRecord(
			final Class<? extends JvmOperation> opClass,
			final Record[] inputs)
		{
			this.opClass = opClass;
			this.inputs = inputs;
		}
		
		@Override
		protected final Record inc(final int amount) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public final String toString() {
			return Arrays.toString(this.inputs) + " " + this.opClass.getSimpleName();
		}
	}
}
