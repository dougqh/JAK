package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.types.Reference;

public final class checkcast extends CastOperation {
	public static final String ID = "checkcast";
	public static final byte CODE = CHECKCAST;
	
	public static final checkcast prototype() {
		return new checkcast( Object.class );
	}
	
	private final Class< ? > aClass;
	
	public checkcast( final Class< ? > aClass ) {
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
	public final Type fromType() {
		return Reference.class;
	}
	
	@Override
	public Type toType() {
		return this.aClass;
	}
	
	@Override
	public final Class< ? >[] getCodeOperandTypes() {
		return new Class< ? >[] { Class.class };
	}
	
	@Override
	public final void process( final JvmOperationProcessor processor ) {
		processor.checkcast( this.aClass );
	}
}