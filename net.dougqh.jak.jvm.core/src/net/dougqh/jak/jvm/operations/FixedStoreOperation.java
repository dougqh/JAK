package net.dougqh.jak.jvm.operations;

public abstract class FixedStoreOperation extends StoreOperation {
	@Override
	public final boolean isFixed() {
		return true;
	}
	
	@Override
	public final boolean equals(Object obj) {
		return (obj != null) && this.getClass().equals(obj.getClass());
	}
	
	@Override
	public final int hashCode() {
		return this.getClass().hashCode();
	}
}
