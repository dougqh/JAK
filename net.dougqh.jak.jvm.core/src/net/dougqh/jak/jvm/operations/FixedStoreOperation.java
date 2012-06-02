package net.dougqh.jak.jvm.operations;


abstract class FixedStoreOperation extends StoreOperation {
	@Override
	public final boolean isFixed() {
		return true;
	}
}
