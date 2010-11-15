package net.dougqh.jak.assembler;

public final class JavaWriter {
	private final TypeWriterGroup typeWriterGroup;
	
	public JavaWriter() {
		this.typeWriterGroup = new TypeWriterGroup();
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
}