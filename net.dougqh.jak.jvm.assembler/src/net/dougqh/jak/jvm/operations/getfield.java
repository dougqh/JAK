package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaField;
import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;
import net.dougqh.jak.types.Any;
import net.dougqh.jak.types.Reference;

public final class getfield extends JvmOperation {
	public static final String ID = "getfield";
	public static final byte CODE = GETFIELD;
	
	public static final getfield prototype() {
		return new getfield( null, null );
	}
	
	private final Type targetType;
	private final JavaField field;
	
	public getfield(
		final Type targetType, 
		final JavaField field )
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
		return new Class< ? >[] { Class.class, JavaField.class };
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
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.getfield( this.targetType, this.field );
	}
}