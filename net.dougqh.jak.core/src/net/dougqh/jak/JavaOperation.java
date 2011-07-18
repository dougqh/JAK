package net.dougqh.jak;

public interface JavaOperation {
	public abstract String getId();
	
	public abstract int getCode();
	
	public abstract String getOperator();
	
	public abstract boolean isPolymorphic();
}
