package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Reference;

public final class anewarray extends BaseJvmOperation {
	public static final String ID = "anewarray";
	public static final byte CODE = ANEWARRAY;
	
	public static final anewarray prototype() {
		return new anewarray( Object.class );
	}
	
	private final Type componentType;
	
	public anewarray( final Type componentType ) {
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
	public final String getOperator() {
		return null;
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
	public final void process( final JvmOperationProcessor processor ) {
		processor.anewarray( this.componentType );
	}
}