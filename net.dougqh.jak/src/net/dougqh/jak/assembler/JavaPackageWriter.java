package net.dougqh.jak.assembler;

public final class JavaPackageWriter {
	private final String name;
	
	JavaPackageWriter( final String name ) {
		this.name = name;
	}
	
	public final JavaClassWriter define(
		final JavaClassDescriptor classDescriptor )
	{
		return new TypeWriterGroup().createClassWriter(
			classDescriptor.typeDescriptor().qualifyWithPackage( this ) );
	}
	
	public final JavaInterfaceWriter define(
		final JavaInterfaceDescriptor interfaceDescriptor )
	{
		return new TypeWriterGroup().createInterfaceWriter(
			interfaceDescriptor.typeDescriptor().qualifyWithPackage( this ) );
	}
	
	public final JavaAnnotationWriter define(
		final JavaAnnotationDescriptor annotationDescriptor )
	{
		return new TypeWriterGroup().createAnnotationWriter(
			annotationDescriptor.typeDescriptor().qualifyWithPackage( this ) );
	}
	
	public final String name() {
		return this.name;
	}
	
	final String qualifyName( final String name ) {
		return this.name() + "." + name;
	}
}
