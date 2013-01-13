package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

public abstract class ReturnOperation extends BaseJvmOperation {
	public abstract Type type();
	
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
		return NO_ARGS;
	}
	
	@Override
	public final Type[] getStackOperandTypes() {
		Type returnType = this.type();
		if ( returnType.equals( void.class ) ) {
			return NO_ARGS;
		} else {
			return new Type[] { returnType };
		}
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		return NO_RESULTS;
	}
	
	@Override
	public final int hashCode() {
		return this.getCode();
	}
	
	@Override
	public final boolean equals(final Object obj) {
		if ( obj == this ) {
			return true;
		} else if ( ! ( obj instanceof ReturnOperation ) ) {
			return false;
		} else {
			ReturnOperation that = (ReturnOperation)obj;
			return ( this.getCode() == that.getCode() );
		}
	}
	
	@Override
	public final String toString() {
		return this.getId();
	}
}
