package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Category1;

public final class dup_x1 extends StackManipulationOperation {
	public static final String ID = "dup_x1";
	public static final byte CODE = DUP_X1;
	
	public static final dup_x1 instance() {
		return new dup_x1();
	}
	
	private dup_x1() {}
	
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
		return new Type[] { Category1.class, Category1.class };
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		return new Type[] { Category1.class, Category1.class, Category1.class };		
	}
	
	@Override
	public final void process( final JvmOperationProcessor writer ) {
		writer.dup_x1();
	}
}