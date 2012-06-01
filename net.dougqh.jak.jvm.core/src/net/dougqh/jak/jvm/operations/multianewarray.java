package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Reference;

public final class multianewarray implements JvmOperation {
	public static final String ID = "multianewarray";
	public static final byte CODE = MULTIANEWARRAY;
	
	public static final multianewarray prototype() {
		return new multianewarray( Object[][].class, 2 );
	}
	
	private final Class< ? > arrayClass;
	private final int numDimensions;
	
	public multianewarray(
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
	public final String getOperator() {
		return null;
	}
	
	@Override
	public final Type[] getCodeOperandTypes() {
		return new Type[] { Class.class, int.class };
	}
	
	@Override
	public final Type[] getStackOperandTypes() {
		//TODO - finish implementing this
		return new Type[] { int.class };
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		//TODO - finish implementing this
		return new Type[] { Reference[].class };
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.multianewarray( this.arrayClass, this.numDimensions );
	}
}