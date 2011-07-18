package net.dougqh.jak.disassembler;

import static net.dougqh.jak.core.Attributes.*;

final class Attribute {
	private final ConstantPool constantPool;
	private final int nameIndex;
	private final byte[] data;
	
	Attribute(
		final ConstantPool constantPool,
		final int nameIndex,
		final byte[] data )
	{
		this.constantPool = constantPool;
		this.nameIndex = nameIndex;
		this.data = data;
	}
	
	final String getName() {
		return this.constantPool.utf8( this.nameIndex );
	}
	
	final boolean isCode() {
		return is( CODE );
	}
	
	final boolean isConstantValue() {
		return is( CONSTANT_VALUE );
	}
	
	final boolean is( final String name ) {
		return this.getName().equals( name );
	}
}
