package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Reference;

public final class newarray implements JvmOperation {
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
	public final String getOperator() {
		return null;
	}
	
	@Override
	public final Type[] getCodeOperandTypes() {
		return new Type[] { Class.class };
	}
	
	@Override
	public final Type[] getStackOperandTypes() {
		return new Type[] { int.class };
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		return new Type[] { Reference[].class };
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.newarray( this.componentClass );
	}
}