package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.types.Reference;

public final class Newarray extends Operation {
	public static final String ID = "newarray";
	public static final byte CODE = NEWARRAY;
	
	public static final Newarray prototype() {
		return new Newarray( Object.class );
	}
	
	private final Class< ? > componentClass;
	
	public Newarray( final Class< ? > componentClass ) {
		this.componentClass = componentClass;
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
		return new Class< ? >[] { int.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { Reference[].class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.newarray( this.componentClass );
	}
}