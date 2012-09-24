package net.dougqh.jak.jvm.operations;

import java.lang.ref.Reference;
import java.lang.reflect.Type;

import net.dougqh.jak.JavaField;
import net.dougqh.jak.types.Any;

public abstract class PutFieldOperation implements JvmOperation {
	final Type targetType;
	final JavaField field;
	
	PutFieldOperation(
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
	public Type[] getStackOperandTypes() {
		if ( this.isStatic() ) {
			return new Type[] { Any.class };
		} else {
			return new Type[] { Reference.class, Any.class };
		}
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		return NO_RESULTS;
	}
	
	@Override
	public final int hashCode() {
		return this.getCode() ^ this.targetType.hashCode() ^ this.field.hashCode();
	}
	
	@Override
	public boolean equals( final Object obj ) {
		if ( obj == this ) {
			return true;
		} else if ( ! ( obj instanceof GetFieldOperation ) ) {
			return false;
		} else {
			GetFieldOperation that = (GetFieldOperation)obj;
			return ( this.getCode() == that.getCode() ) &&
				this.targetType.equals( that.targetType ) &&
				this.field.equals( that.field );
		}
	}
	
	@Override
	public final String toString() {
		return this.getId() + " " + this.targetType + " " + this.field;
	}
}
