package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;
import net.dougqh.jak.types.Category1;
import net.dougqh.jak.types.Category2;

public final class Dup_x2 extends Operation {
	public static final String ID = "dup_x2";
	public static final byte CODE = DUP_X2;
	
	public static final Dup_x2 instance() {
		return new Dup_x2( true );
	}
	
	public static final Dup_x2 form2Instance() {
		return new Dup_x2( false );
	}
	
	private final boolean form1;
	
	private Dup_x2( final boolean form1 ) {
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
	public final Class< ? >[] getCodeOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		if ( this.form1 ) {
			return new Class< ? >[] { Category1.class, Category1.class };
		} else {
			return new Class< ? >[] { Category2.class };
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
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.dup_x2();
	}
}