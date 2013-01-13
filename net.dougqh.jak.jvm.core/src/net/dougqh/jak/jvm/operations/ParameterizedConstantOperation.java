package net.dougqh.jak.jvm.operations;


public abstract class ParameterizedConstantOperation extends ConstantOperation {
	public final boolean isFixed() {
		return false;
	}
	
	@Override
	public String toString() {
		return this.getId() + " " + this.value();
	}
}
