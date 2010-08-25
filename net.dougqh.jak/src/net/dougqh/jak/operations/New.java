package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.types.Reference;

public final class New extends Operation {
	public static final String ID = "new";
	public static final byte CODE = NEW;
	
	public static final New prototype() {
		return new New( Object.class );
	}
	
	private final Class< ? > aClass;
	
	public New( final Class< ? > aClass ) {
		this.aClass = aClass;
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
		return new Class< ? >[] { Class.class };
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { Reference.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.new_( this.aClass );
	}
}