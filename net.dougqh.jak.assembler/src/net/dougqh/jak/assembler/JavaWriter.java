package net.dougqh.jak.assembler;

public final class JavaWriter {
	private final TypeWriterGroup typeWriterGroup;
	
	public JavaWriter() {
		this.typeWriterGroup = new TypeWriterGroup();
	}
	
	public JavaWriter( final ClassLoader classLoader ) {
		this.typeWriterGroup = new TypeWriterGroup( classLoader );
	}
	
	public final JavaPackageWriter define(
		final JavaPackageDescriptor packageDescriptor )
	{
		return this.typeWriterGroup.createPackageWriter( packageDescriptor.name() );
	}
	
	public final JavaClassWriter define(
		final JavaClassDescriptor classDescriptor )
	{
		return this.typeWriterGroup.createClassWriter( classDescriptor.typeDescriptor() );
	}
	
	public final JavaInterfaceWriter define(
		final JavaInterfaceDescriptor interfaceDescriptor )
	{
		return this.typeWriterGroup.createInterfaceWriter( interfaceDescriptor.typeDescriptor() );
	}
	
	public final JavaAnnotationWriter define(
		final JavaAnnotationDescriptor annotationDescriptor )
	{
		return this.typeWriterGroup.createAnnotationWriter( annotationDescriptor.typeDescriptor() );
	}
}