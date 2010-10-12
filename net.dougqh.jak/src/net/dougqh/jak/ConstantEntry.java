package net.dougqh.jak;

public final class ConstantEntry {
	private final int index;
	private final Class< ? > type;
	private final Object value;
	
	ConstantEntry(
		final int index,
		final Class< ? > type,
		final Object value )
	{
		this.index = index;
		this.type = type;
		this.value = value;
	}
	
	public final int index() {
		return this.index;
	}
	
	public final Class< ? > type() {
		return this.type;
	}
	
	public final Object value() {
		return this.value;
	}
}
