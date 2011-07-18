package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;
import net.dougqh.jak.types.Reference;

public final class anewarray extends JvmOperation {
	public static final String ID = "anewarray";
	public static final byte CODE = ANEWARRAY;
	
	public static final anewarray prototype() {
		return new anewarray( Object.class );
	}
	
	private final Class< ? > componentType;
	
	public anewarray( final Class< ? > componentType ) {
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
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.anewarray( this.componentType );
	}
}