package net.dougqh.jak.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.JavaFieldDescriptor;
import net.dougqh.jak.types.Any;
import net.dougqh.jak.types.Reference;

public final class Putfield extends Operation {
	public static final String ID = "putfield";
	public static final byte CODE = PUTFIELD;
	
	public static final Putfield prototype() {
		return new Putfield( null, null );
	}
	
	private final Type targetType;
	private final JavaFieldDescriptor field;
	
	public Putfield(
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
		return new Class< ? >[] { Reference.class, Any.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return NO_RESULTS;
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.putfield( this.targetType, this.field );
	}
}