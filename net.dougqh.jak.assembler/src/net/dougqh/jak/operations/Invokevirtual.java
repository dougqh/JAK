package net.dougqh.jak.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.assembler.JavaCoreCodeWriter;
import net.dougqh.jak.assembler.JavaMethodDescriptor;
import net.dougqh.jak.types.Any;
import net.dougqh.jak.types.ArgList;
import net.dougqh.jak.types.Reference;

public final class Invokevirtual extends Operation {
	public static final String ID = "invokevirtual";
	public static final byte CODE = INVOKEVIRTUAL;
	
	public static final Invokevirtual prototype() {
		return new Invokevirtual( null, null );
	}
	
	private final Type targetType;
	private final JavaMethodDescriptor method;
	
	public Invokevirtual(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.targetType = targetType;
		this.method = method;
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
		return new Class< ? >[] { Type.class, JavaMethodDescriptor.class };
	}
	
	@Override
	public final Class< ? >[] getStackOperandTypes() {
		return new Class< ? >[] { Reference.class, ArgList.class };
	}
	
	@Override
	public final Class< ? >[] getStackResultTypes() {
		return new Class< ? >[] { Any.class };
	}
	
	@Override
	public final void write( final JavaCoreCodeWriter writer ) {
		writer.invokevirtual( this.targetType, this.method );
	}
}