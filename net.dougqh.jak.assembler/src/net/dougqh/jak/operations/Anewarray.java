package net.dougqh.jak.operations;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;
import net.dougqh.jak.types.Reference;

public final class Anewarray extends Operation {
	public static final String ID = "anewarray";
	public static final byte CODE = ANEWARRAY;
	
	public static final Anewarray prototype() {
		return new Anewarray( Object.class );
	}
	
	private final Class< ? > componentType;
	
	public Anewarray( final Class< ? > componentType ) {
		this.componentType = componentType;
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
		writer.anewarray( this.componentType );
	}
}