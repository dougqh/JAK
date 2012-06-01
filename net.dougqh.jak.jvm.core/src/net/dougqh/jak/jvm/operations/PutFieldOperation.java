package net.dougqh.jak.jvm.operations;

import java.lang.ref.Reference;
import java.lang.reflect.Type;

import net.dougqh.jak.JavaField;
import net.dougqh.jak.types.Any;

public abstract class PutFieldOperation implements JvmOperation {
	public abstract boolean isStatic();
	
	@Override
	public final boolean isPolymorphic() {
		return false;
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
}
