package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Category1;
import net.dougqh.jak.types.Category2;

public final class dup2_x1 extends StackManipulationOperation {
	public static final String ID = "dup2_x1";
	public static final byte CODE = DUP2_X1;
	
	public static final dup2_x1 instance() {
		return new dup2_x1( true );
	}
	
	public static final dup2_x1 form2Instance() {
		return new dup2_x1( false );
	}
	
	private final boolean form1;
	
	private dup2_x1( final boolean form1 ) {
		this.form1 = form1;
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
	
	@Override
	public final Type[] getStackOperandTypes() {
		//TODO : Double-check that this correct
		if ( this.form1 ) {
			return new Type[] {
				Category1.class,
				Category1.class,
				Category1.class
			};
		} else {
			return new Type[] { 
				Category1.class,
				Category2.class
			};
		}
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		//TODO : Double-check that this correct
		if ( this.form1 ) {
			return new Type[] {
				Category1.class,
				Category1.class,
				Category1.class,
				Category1.class,
				Category1.class
			};
		} else {
			return new Type[] {
				Category2.class,
				Category1.class,
				Category2.class
			};
		}
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.dup2_x1();
	}
}