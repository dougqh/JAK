package net.dougqh.jak.jvm.assembler;

import net.dougqh.jak.JavaAnnotationDescriptor;
import net.dougqh.jak.JavaClassDescriptor;
import net.dougqh.jak.JavaInterfaceDescriptor;

public final class JvmPackageWriter {
	private final TypeWriterGroup writerGroup;
	private final String name;
	
	JvmPackageWriter(
		final TypeWriterGroup writerGroup,
		final String name )
	{
		this.writerGroup = writerGroup;
		this.name = name;
	}
	
	public final JvmClassWriter define(
		final JavaClassDescriptor classDescriptor )
	{
		return this.writerGroup.createClassWriter(
			classDescriptor.typeDescriptor().qualify( this.name ) );
	}
	
	public final JvmInterfaceWriter define(
		final JavaInterfaceDescriptor interfaceDescriptor )
	{
		return this.writerGroup.createInterfaceWriter(
			interfaceDescriptor.typeDescriptor().qualify( this.name ) );
	}
	
	public final JvmAnnotationWriter define(
		final JavaAnnotationDescriptor annotationDescriptor )
	{
		return this.writerGroup.createAnnotationWriter(
			annotationDescriptor.typeDescriptor().qualify( this.name ) );
	}
	
	public final String name() {
		return this.name;
	}
	
	final String qualifyName( final String name ) {
		return this.name() + "." + name;
	}
}
