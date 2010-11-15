package net.dougqh.jak.assembler;

import net.dougqh.jak.types.Category1;
import net.dougqh.jak.types.Category2;

public final class ConstantEntry {
	private final int index;
	private final Class< ? > type;
	private final Object value;
	
	public static final ConstantEntry category1( final int index ) {
		return new ConstantEntry( index, Category1.class, null );
	}
	
	public static final ConstantEntry category2( final int index ) {
		return new ConstantEntry( index, Category2.class, null );
	}
	
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
