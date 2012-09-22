package net.dougqh.jak.disassembler;

import static net.dougqh.jak.jvm.Attributes.*;

final class Attribute {
	private final ConstantPool constantPool;
	private final int nameIndex;
	private final JvmInputStream in;
	
	Attribute(
		final ConstantPool constantPool,
		final int nameIndex,
		final JvmInputStream in )
	{
		this.constantPool = constantPool;
		this.nameIndex = nameIndex;
		this.in = in;
		this.in.enableReset();
	}
	
	final String getName() {
		return this.constantPool.utf8( this.nameIndex );
	}
	
	final JvmInputStream in() {
		this.in.reset();
		return this.in;
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
