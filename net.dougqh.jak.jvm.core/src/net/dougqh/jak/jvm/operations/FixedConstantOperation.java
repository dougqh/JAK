package net.dougqh.jak.jvm.operations;


public abstract class FixedConstantOperation extends ConstantOperation {
	public final boolean isFixed() {
		return true;
	}
	
	@Override
	public String toString() {
		return this.getId();
	}
}
