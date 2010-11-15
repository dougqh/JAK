package net.dougqh.jak.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;
import net.dougqh.jak.assembler.JavaFieldDescriptor;
import net.dougqh.jak.types.Any;

public final class Putstatic extends Operation {
	public static final String ID = "putstatic";
	public static final byte CODE = PUTSTATIC;
	
	public static final Putstatic prototype() {
		return new Putstatic( null, null );
	}
	
	private final Type targetType;
	private final JavaFieldDescriptor field;
	
	public Putstatic( 
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
	public final boolean isPolymorphic() {
		return true;
	}
	
	@Override
	public final Class< ? >[] getCodeOperandTypes() {
		return new Class< ? >[] { Class.class, JavaFieldDescriptor.class };
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		return new Class< ? >[] { Any.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return NO_RESULTS;
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.putstatic( this.targetType, this.field );
	}
}