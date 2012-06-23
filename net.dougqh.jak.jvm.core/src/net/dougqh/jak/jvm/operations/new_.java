package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Reference;

public final class new_ implements JvmOperation {
	public static final String ID = "new";
	public static final byte CODE = NEW;
	
	public static final new_ prototype() {
		return new new_( Object.class );
	}
	
	private final Type aClass;
	
	public new_( final Type aClass ) {
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
		return NO_ARGS;
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		return new Type[] { Reference.class };
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.new_( this.aClass );
	}
}