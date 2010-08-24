package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.Operation;

public final class Dup2_x2 extends Operation {
	public static final String ID = "dup2_x2";
	public static final byte CODE = DUP2_X2;
	
	public static final Dup2_x2 instance() {
		return new Dup2_x2( 1 );
	}
	
	public static final Dup2_x2 form2Instance() {
		return new Dup2_x2( 2 );
	}
	
	public static final Dup2_x2 form3Instance() {
		return new Dup2_x2( 3 );
	}
	
	public static final Dup2_x2 form4Instance() {
		return new Dup2_x2( 4 );
	}
	
	//private final int form;
	
	private Dup2_x2( final int form ) {
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
	public final Class< ? >[] getCodeOperandTypes() {
		return NO_ARGS;
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
	public final Class< ? >[] getStackOperandTypes() {
		//TODO: Finish implementing this
		return null;
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		//TODO: Finish implementing this
		return null;
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.dup2_x2();
	}
}