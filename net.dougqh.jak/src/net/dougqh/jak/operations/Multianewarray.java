package net.dougqh.jak.operations;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.types.Reference;

public final class Multianewarray extends Operation {
	public static final String ID = "multianewarray";
	public static final byte CODE = MULTIANEWARRAY;
	
	public static final Multianewarray prototype() {
		return new Multianewarray( Object[][].class, 2 );
	}
	
	private final Class< ? > arrayClass;
	private final int numDimensions;
	
	public Multianewarray(
		final Class< ? > arrayClass,
		final int numDimensions )
	{
		this.arrayClass = arrayClass;
		this.numDimensions = numDimensions;
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
		return new Class< ? >[] { Class.class, int.class };
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		//TODO - finish implementing this
		return new Class< ? >[] { int.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		//TODO - finish implementing this
		return new Class< ? >[] { Reference[].class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.multianewarray(
			this.arrayClass,
			this.numDimensions );
	}
}