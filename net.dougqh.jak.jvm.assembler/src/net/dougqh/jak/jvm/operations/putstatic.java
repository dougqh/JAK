package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaField;
import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;
import net.dougqh.jak.types.Any;

public final class putstatic extends JvmOperation {
	public static final String ID = "putstatic";
	public static final byte CODE = PUTSTATIC;
	
	public static final putstatic prototype() {
		return new putstatic( null, null );
	}
	
	private final Type targetType;
	private final JavaField field;
	
	public putstatic( 
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
		return new Class< ? >[] { Any.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return NO_RESULTS;
	}
	
	@Override
	public final void write( final JvmCoreCodeWriter writer ) {
		writer.putstatic( this.targetType, this.field );
	}
}