package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaField;
import net.dougqh.jak.types.Any;
import net.dougqh.jak.types.Reference;

public abstract class GetFieldOperation implements JvmOperation {	
	final Type targetType;
	final JavaField field;
	
	GetFieldOperation(
		final Type targetType,
		final JavaField field )
	{
		this.targetType = targetType;
		this.field = field;
	}

	public abstract boolean isStatic();
	
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
		return new Type[] { Class.class, JavaField.class };
	}
	
	@Override
	public final Type[] getStackOperandTypes() {
		if ( this.isStatic() ) {
			return new Type[] { Any.class };
		} else {
			return new Type[] { Reference.class, Any.class };
		}
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		return new Type[] { Any.class };
	}
}
