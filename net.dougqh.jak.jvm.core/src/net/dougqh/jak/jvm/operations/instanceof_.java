package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Reference;

public final class instanceof_ extends BaseJvmOperation {
	public static final String ID = "instanceof";
	public static final byte CODE = INSTANCEOF;
	
	public static final instanceof_ prototype() {
		return new instanceof_( Object.class );
	}
	
	private final Type aClass;
	
	public instanceof_( final Type aClass ) {
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
	public final boolean isPolymorphic() {
		return false;
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
		return new Type[] { Reference.class };
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		return new Type[] { boolean.class };
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.instanceof_( this.aClass );
	}
}