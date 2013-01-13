package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.types.Any;
import net.dougqh.jak.types.ArgList;
import net.dougqh.jak.types.Reference;

public abstract class InvocationOperation extends BaseJvmOperation {
	protected final Type targetType;
	protected final JavaMethodDescriptor method;
	
	InvocationOperation(
		final Type targetType,
		final JavaMethodDescriptor method )
	{
		this.targetType = targetType;
		this.method = method;
	}

	public abstract boolean isStatic();
	
	public final Type getType() {
		return this.targetType;
	}
	
	public final JavaMethodDescriptor getMethod() {
		return this.method;
	}
	
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
		return new Type[] { Type.class, JavaMethodDescriptor.class };
	}
	
	@Override
	public final Type[] getStackOperandTypes() {
		if ( this.isStatic() ) {
			return new Type[] { ArgList.class };
		} else {
			return new Type[] { Reference.class, ArgList.class };
		}
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		return new Type[] { Any.class };
	}
	
	@Override
	public int hashCode() {
		//TODO: Provide a better implementation of hashCode
		return this.getCode();
	}
	
	@Override
	public final boolean equals(Object obj) {
		if ( obj == this ) {
			return true;
		} else if ( ! ( obj instanceof InvocationOperation ) ) {
			return false;
		} else {
			InvocationOperation that = (InvocationOperation)obj;
			return ( this.getCode() == that.getCode() ) &&
				( this.targetType.equals( that.targetType ) ) &&
				( this.method.equals( that.method ) );
		}
	}
	
	@Override
	public final String toString() {
		return this.getId() + " " + this.targetType + " " + this.method;
	}
}
