package net.dougqh.jak.disassembler;

import java.io.IOException;
import java.util.AbstractList;
import java.util.List;

final class Interfaces {
	private final ConstantPool constantPool;
	private final int[] interfaceIndices;
	
	Interfaces(
		final ConstantPool constantPool,
		final JvmInputStream in )
		throws IOException
	{
		this.constantPool = constantPool;
		
		int count = in.u2();
		
		this.interfaceIndices = new int[ count ];
		for ( int i = 0; i < count; ++i ) {
			this.interfaceIndices[ i ] = in.u2();
		}
	}
	
	final List< String > getNames() {
		return new ListImpl();
	}
	
	private final class ListImpl extends AbstractList< String > {
		@Override
		public final int size() {
			return Interfaces.this.interfaceIndices.length;
		}
		
		@Override
		public final String get( final int index ) {
			return Interfaces.this.constantPool.typeName(
				Interfaces.this.interfaceIndices[ index ] );
		}
	}
}
