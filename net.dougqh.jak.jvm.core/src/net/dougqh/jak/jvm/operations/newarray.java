package net.dougqh.jak.jvm.operations;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Reference;

public final class newarray extends BaseJvmOperation {
	public static final String ID = "newarray";
	public static final byte CODE = NEWARRAY;
	
	public static final newarray prototype() {
		return new newarray( Object.class );
	}
	
	private final Class< ? > componentClass;
	
	public newarray( final Class< ? > componentClass ) {
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
	public final void write( final JvmOperationProcessor writer ) {
		writer.newarray( this.componentClass );
	}
}