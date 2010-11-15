package net.dougqh.jak.assembler;

import java.lang.reflect.Type;

final class TypeDescriptor {
	private final int flags;
	private final String name;
	private final Type parentType;
	private final Type[] interfaceTypes;
	
	TypeDescriptor(
		final int flags,
		final String name,
		final Type parentType,
		final Type[] interfaceTypes )
	{
		this.flags = flags;
		this.name = name;
		this.parentType = parentType;
		this.interfaceTypes = interfaceTypes;
	}
	
//	TypeDescriptor(
//		final JavaPackageWriter packageBuilder,
//		final TypeDescriptor typeBuilder )
//	{
//		this.flags = typeBuilder.flags;
//		this.fullName = packageBuilder.qualifyName( typeBuilder.fullName );
//	}
//	
//	TypeDescriptor(
//		final TypeWriter typeWriter,
//		final TypeDescriptor typeBuilder )
//	{
//		this.flags = typeBuilder.flags;
//		this.fullName = typeWriter.qualifyName( typeBuilder.fullName );
//	}
	
	protected final JavaVersion version() {
		return JavaVersion.getDefault();
	}
	
	protected final int flags() {
		return this.flags;
	}
	
	protected final String name() {
		return this.name;
	}
	
	protected final Type parentType() {
		return this.parentType;
	}
	
	protected final Type[] interfaceTypes() {
		return this.interfaceTypes;
	}
	
	protected final TypeDescriptor qualifyWithPackage(
		final JavaPackageWriter packageWriter )
	{
		return new TypeDescriptor(
			this.flags,
			packageWriter.qualifyName( this.name ),
			this.parentType,
			this.interfaceTypes );
	}
	
	protected final TypeDescriptor innerType(
		final TypeWriter outerWriter,
		final int additionalFlags )
	{
		return new TypeDescriptor(
			this.flags | additionalFlags,
			outerWriter.qualifyName( this.name ),
			this.parentType,
			this.interfaceTypes );
	}
}
