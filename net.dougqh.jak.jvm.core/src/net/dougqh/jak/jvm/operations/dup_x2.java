package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Category1;
import net.dougqh.jak.types.Category2;

public final class dup_x2 extends StackManipulationOperation {
	public static final String ID = "dup_x2";
	public static final byte CODE = DUP_X2;
	
	public static final dup_x2 instance() {
		return new dup_x2( true );
	}
	
	public static final dup_x2 form2Instance() {
		return new dup_x2( false );
	}
	
	private final boolean form1;
	
	private dup_x2( final boolean form1 ) {
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
		if ( this.form1 ) {
			return new Type[] { Category1.class, Category1.class };
		} else {
			return new Type[] { Category2.class };
		}
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		if ( this.form1 ) {
			return new Class< ? >[] { 
				Category1.class,
				Category1.class,
				Category1.class,
				Category1.class
			};
		} else {
			return new Class< ? >[] { 
				Category2.class,
				Category2.class
			};
		}
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.dup_x2();
	}
}