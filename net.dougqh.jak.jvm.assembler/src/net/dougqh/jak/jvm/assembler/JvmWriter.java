package net.dougqh.jak.jvm.assembler;

import net.dougqh.jak.JavaAnnotationDescriptor;
import net.dougqh.jak.JavaClassDescriptor;
import net.dougqh.jak.JavaInterfaceDescriptor;
import net.dougqh.jak.JavaPackageDescriptor;
import net.dougqh.jak.assembler.JakWriter;

public final class JvmWriter implements JakWriter {
	private final TypeWriterGroup typeWriterGroup;
	
	public JvmWriter() {
		this.typeWriterGroup = new TypeWriterGroup();
	}
	
	public JvmWriter( final ClassLoader classLoader ) {
		this.typeWriterGroup = new TypeWriterGroup( classLoader );
	}
	
	public final JvmPackageWriter define(
		final JavaPackageDescriptor packageDescriptor )
	{
		return this.typeWriterGroup.createPackageWriter( packageDescriptor.name() );
	}
	
	@Override
	public final JvmClassWriter define(
		final JavaClassDescriptor classDescriptor )
	{
		return this.typeWriterGroup.createClassWriter( classDescriptor.typeDescriptor() );
	}
	
	@Override
	public final JvmInterfaceWriter define(
		final JavaInterfaceDescriptor interfaceDescriptor )
	{
		return this.typeWriterGroup.createInterfaceWriter( interfaceDescriptor.typeDescriptor() );
	}
	
	@Override
	public final JvmAnnotationWriter define(
		final JavaAnnotationDescriptor annotationDescriptor )
	{
		return this.typeWriterGroup.createAnnotationWriter( annotationDescriptor.typeDescriptor() );
	}
}