package net.dougqh.jak.disassembler;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaField;

public final class JvmField extends JavaField {
	private final ConstantPool constantPool;
	
	private final int flags;
	private final int nameIndex;
	private final int descriptorIndex;
	private final Attributes attributes;
	
	JvmField(
		final ConstantPool constantPool,
		final int flags,
		final int nameIndex,
		final int descriptorIndex,
		final Attributes attributes )
	{
		this.constantPool = constantPool;
		
		this.flags = flags;
		this.nameIndex = nameIndex;
		this.descriptorIndex = descriptorIndex;
		this.attributes = attributes;
	}
	
	@Override
	public final int getFlags() {
		//TODO: Fill getFlags out
		throw new UnsupportedOperationException( "incomplete" );
	}
	
	@Override
	public final Type getType() {
		//TODO: Fill getType out
		throw new UnsupportedOperationException( "incomplete" );
	}
	
	public final String getName() {
		return this.constantPool.utf8( this.nameIndex );
	}
}
