package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.types.Reference;

public final class Instanceof extends Operation {
	public static final String ID = "instanceof";
	public static final byte CODE = INSTANCEOF;
	
	public static final Instanceof prototype() {
		return new Instanceof( Object.class );
	}
	
	private final Class< ? > aClass;
	
	public Instanceof( final Class< ? > aClass ) {
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
		return new Class< ? >[] { Reference.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { boolean.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.instanceof_( this.aClass );
	}
}