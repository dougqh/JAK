package net.dougqh.jak;

import java.lang.reflect.Type;


public abstract class JavaField implements JavaElement {
	public abstract int getFlags();
	
	public abstract Type getType();
	
	public abstract String getName();
	
	public final int hashCode() {
		int prime = 37;
		
		int result = 0;
		result = result * prime ^ this.getType().hashCode();
		result = result * prime ^ this.getName().hashCode();
		return result;
	}
	
	@Override
	public final boolean equals( final Object obj ) {
		if ( obj == this ) {
			return true;
		} else if ( ! ( obj instanceof JavaField ) ) {
			return false;
		} else {
			JavaField that = (JavaField)obj;
			return this.getType().equals( that.getType() ) &&
				this.getName().equals( that.getName() );
		}
	}
	
	@Override
	public final String toString() {
		return this.getType() + " " + this.getName();
	}
}
