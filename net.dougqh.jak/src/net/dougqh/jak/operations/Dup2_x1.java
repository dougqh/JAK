package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.types.Category1;
import net.dougqh.jak.types.Category2;

public final class Dup2_x1 extends Operation {
	public static final String ID = "dup2_x1";
	public static final byte CODE = DUP2_X1;
	
	public static final Dup2_x1 instance() {
		return new Dup2_x1( true );
	}
	
	public static final Dup2_x1 form2Instance() {
		return new Dup2_x1( false );
	}
	
	private final boolean form1;
	
	private Dup2_x1( final boolean form1 ) {
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
	public final Class< ? >[] getCodeOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		//TODO : Double-check that this correct
		if ( this.form1 ) {
			return new Class< ? >[] {
				Category1.class,
				Category1.class,
				Category1.class
			};
		} else {
			return new Class< ? >[] { 
				Category1.class,
				Category2.class
			};
		}
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		//TODO : Double-check that this correct
		if ( this.form1 ) {
			return new Class< ? >[] {
				Category1.class,
				Category1.class,
				Category1.class,
				Category1.class,
				Category1.class
			};
		} else {
			return new Class< ? >[] {
				Category2.class,
				Category1.class,
				Category2.class
			};
		}
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.dup2_x1();
	}
}