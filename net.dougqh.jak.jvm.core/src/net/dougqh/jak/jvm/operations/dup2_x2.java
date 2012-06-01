package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Category2;

public final class dup2_x2 extends StackManipulationOperation {
	public static final String ID = "dup2_x2";
	public static final byte CODE = DUP2_X2;
	
	public static final dup2_x2 instance() {
		return new dup2_x2( 1 );
	}
	
	public static final dup2_x2 form2Instance() {
		return new dup2_x2( 2 );
	}
	
	public static final dup2_x2 form3Instance() {
		return new dup2_x2( 3 );
	}
	
	public static final dup2_x2 form4Instance() {
		return new dup2_x2( 4 );
	}
	
	//private final int form;
	
	private dup2_x2( final int form ) {
		//this.form = form;
	}
	
	@Override
	public final String getId() {
		return ID;
	}
	
	@Override
	public final int getCode() {
		return CODE;
	}

	@Override
	public final boolean isPolymorphic() {
		return true;
	}
	
//	Form 1:
//		..., value4, value3, value2, value1  ..., value2, value1, value4, value3, value2, value1
//		where value1, value2, value3, and value4 are all values of a category 1 computational type (¤3.11.1).
//		Form 2:
//
//		..., value3, value2, value1  ..., value1, value3, value2, value1
//		where value1 is a value of a category 2 computational type and value2 and value3 are both values of a category 1 computational type (¤3.11.1).
//		Form 3:
//
//		..., value3, value2, value1  ..., value2, value1, value3, value2, value1
//		where value1 and value2 are both values of a category 1 computational type and value3 is a value of a category 2 computational type (¤3.11.1).
//		Form 4:
//
//		..., value2, value1  ..., value1, value2, value1
//		where value1 and value2 are both values of a category 2 computational type (¤3.11.1).
//		
	@Override
	public final Type[] getStackOperandTypes() {
		//TODO: Finish implementing this - currently incorrect
		return new Type[] { Category2.class };
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		//TODO: Finish implementing this - currently incorrect
		return new Type[] { Category2.class };
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.dup2_x2();
	}
}