package net.dougqh.jak.jvm.operations;


abstract class FixedLoadOperation extends LoadOperation {
	@Override
	public final boolean isFixed() {
		return true;
	}
}
