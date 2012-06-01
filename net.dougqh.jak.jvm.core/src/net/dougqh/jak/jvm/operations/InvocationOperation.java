package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.types.Any;
import net.dougqh.jak.types.ArgList;
import net.dougqh.jak.types.Reference;

public abstract class InvocationOperation implements JvmOperation {
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
}
