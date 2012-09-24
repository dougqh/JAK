package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

public abstract class StoreOperation implements JvmOperation{
	public abstract Type type();
	
	public abstract int slot();
	
	public abstract boolean isFixed();
	
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
		if ( this.isFixed() ) {
			return NO_ARGS;
		} else {
			return new Type[] { int.class };
		}
	}
	
	@Override
	public final Type[] getStackOperandTypes() {
		return new Type[] { this.type() };
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		return NO_RESULTS;
	}
	
	@Override
	public final String toString() {
		if ( this.isFixed() ) {
			return this.getId();
		} else {
			return this.getId() + " " + this.slot();
		}
	}
}
