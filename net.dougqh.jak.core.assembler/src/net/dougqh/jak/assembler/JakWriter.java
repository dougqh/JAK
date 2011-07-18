package net.dougqh.jak.assembler;

import net.dougqh.jak.JavaAnnotationDescriptor;
import net.dougqh.jak.JavaClassDescriptor;
import net.dougqh.jak.JavaInterfaceDescriptor;

public interface JakWriter {
	public abstract JakClassWriter define(
		final JavaClassDescriptor classDescriptor );
	
	public abstract JakInterfaceWriter define(
		final JavaInterfaceDescriptor interfaceDescriptor );
	
	public abstract JakAnnotationWriter define(
		final JavaAnnotationDescriptor annotationDescriptor );
}
