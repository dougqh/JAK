package net.dougqh.jak.disassembler;

import static net.dougqh.jak.Methods.*;

import java.lang.reflect.Type;
import java.util.List;

public final class JvmMethod implements JavaMethod {
	private final ConstantPool constantPool;
	
	private final int flags;
	private final int nameIndex;
	private final int descriptorIndex;
	private final Attributes attributes;
	
	JvmMethod(
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
	public final String getName() {
		return this.constantPool.utf8( this.nameIndex );
	}
	
	@Override
	public final boolean isConstructor() {
		return this.getName().equals( INIT );
	}
	
	@Override
	public final boolean isClassInitializer() {
		return this.getName().equals( CLINIT );
	}
	
	@Override
	public final Type getReturnType() {
		throw new UnsupportedOperationException( "incomplete" );
	}
	
	@Override
	public final List< Type > getParameterTypes() {
		throw new UnsupportedOperationException( "incomplete" );
	}
	
	private final CodeAttribute getCode() {
		return this.attributes.getCode();
	}
	
	public final boolean hasCode() {
		return ( this.getCode() != null );
	}
	
	public final int getCodeLength() {
		CodeAttribute code = this.getCode();
		return ( code == null ) ? -1 : code.length();
	}
	
	public final int getMaxStack() {
		CodeAttribute code = this.getCode();
		return ( code == null ) ? 0 : code.maxStack();
	}

	public final int getMaxLocals() {
		CodeAttribute code = this.getCode();
		return ( code == null ) ? 0 : code.maxLocals();
	}
	
	public final String toString() {
		return this.getName();
	}
}
