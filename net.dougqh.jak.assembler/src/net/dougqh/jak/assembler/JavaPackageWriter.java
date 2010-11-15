package net.dougqh.jak.assembler;

public final class JavaPackageWriter {
	private final TypeWriterGroup writerGroup;
	private final String name;
	
	JavaPackageWriter(
		final TypeWriterGroup writerGroup,
		final String name )
	{
		this.writerGroup = writerGroup;
		this.name = name;
	}
	
	public final JavaClassWriter define(
		final JavaClassDescriptor classDescriptor )
	{
		return this.writerGroup.createClassWriter(
			classDescriptor.typeDescriptor().qualifyWithPackage( this ) );
	}
	
	public final JavaInterfaceWriter define(
		final JavaInterfaceDescriptor interfaceDescriptor )
	{
		return this.writerGroup.createInterfaceWriter(
			interfaceDescriptor.typeDescriptor().qualifyWithPackage( this ) );
	}
	
	public final JavaAnnotationWriter define(
		final JavaAnnotationDescriptor annotationDescriptor )
	{
		return this.writerGroup.createAnnotationWriter(
			annotationDescriptor.typeDescriptor().qualifyWithPackage( this ) );
	}
	
	public final String name() {
		return this.name;
	}
	
	final String qualifyName( final String name ) {
		return this.name() + "." + name;
	}
}
