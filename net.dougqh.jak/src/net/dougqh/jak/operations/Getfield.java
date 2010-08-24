package net.dougqh.jak.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.JavaFieldDescriptor;
import net.dougqh.jak.Operation;
import net.dougqh.jak.types.Any;
import net.dougqh.jak.types.Reference;

public final class Getfield extends Operation {
	public static final String ID = "getfield";
	public static final byte CODE = GETFIELD;
	
	public static final Getfield prototype() {
		return new Getfield( null, null );
	}
	
	private final Type targetType;
	private final JavaFieldDescriptor field;
	
	public Getfield(
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
		return new Class< ? >[] { Reference.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { Any.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.getfield( this.targetType, this.field );
	}
}