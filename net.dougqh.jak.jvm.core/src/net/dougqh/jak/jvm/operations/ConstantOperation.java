package net.dougqh.jak.jvm.operations;

import java.lang.reflect.Type;

public abstract class ConstantOperation extends BaseJvmOperation {
	public Type codeInputType() {
		return null;
	}
	
	public abstract Type type();
	
	public abstract boolean isFixed();
	
	public abstract <T> T value();
	
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
		Type codeInputType = this.codeInputType();
		return codeInputType == null ? NO_ARGS : new Type[] { codeInputType };
	}
	
	@Override
	public final Type[] getStackOperandTypes() {
		return NO_ARGS;
	}
	
	@Override
	public final Type[] getStackResultTypes() {
		return new Type[] { this.type() };
	}
	
	protected static final <T> T as(final Object value) {
		@SuppressWarnings("unchecked")
		T castedValue = (T)value;
		return castedValue;
	}
	
	@Override
	public final boolean equals(final Object obj) {
		if ( obj == null ) {
			return false;
		} else if ( obj == this ) {
			return true;
		} else if ( ! this.getClass().equals(obj.getClass()) ) {
			return false;
		} else {
			ConstantOperation that = (ConstantOperation)obj;
			return ( this.value() == that.value() ) ||
				this.value().equals( that.value() );
		}
	}
	
	@Override
	public int hashCode() {
		Object value = this.value();
		return this.getClass().hashCode() ^ ( value == null ? 0 : value.hashCode() );
	}
	
	@Override
	public String toString() {
		Object value = this.value();
		return ( value == null ) ? "null" : value.toString();
	}
}
