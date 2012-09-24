package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaField;

public abstract class FieldOperation implements JvmOperation {
	final Type targetType;
	final JavaField field;
	
	FieldOperation(
		final Type targetType,
		final JavaField field )
	{
		this.targetType = targetType;
		this.field = field;
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
