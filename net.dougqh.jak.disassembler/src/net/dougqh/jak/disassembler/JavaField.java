package net.dougqh.jak.disassembler;

public final class JavaField {
	private final ConstantPool constantPool;
	
	private final int flags;
	private final int nameIndex;
	private final int descriptorIndex;
	private final Attributes attributes;
	
	JavaField(
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
	
	public final String getName() {
		return this.constantPool.utf8( this.nameIndex );
	}

	public final String toString() {
		return this.getName();
	}
}
