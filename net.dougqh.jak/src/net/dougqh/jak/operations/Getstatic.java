package net.dougqh.jak.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.JavaFieldDescriptor;
import net.dougqh.jak.types.Any;

public final class Getstatic extends Operation {
	public static final String ID = "getstatic";
	public static final byte CODE = GETSTATIC;
	
	public static final Getstatic prototype() {
		return new Getstatic( null, null );
	}
	
	private final Type targetType;
	private final JavaFieldDescriptor field;
	
	public Getstatic(
		final Type targetType,
		final JavaFieldDescriptor field )
	{
		this.targetType = targetType;
		this.field = field;
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
	public final Class< ? >[] getCodeOperandTypes() {
		return new Class< ? >[] { Class.class, JavaFieldDescriptor.class };
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { Any.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.getstatic( this.targetType, this.field );
	}
}