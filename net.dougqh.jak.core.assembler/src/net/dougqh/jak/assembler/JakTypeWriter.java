package net.dougqh.jak.assembler;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaAnnotationDescriptor;
import net.dougqh.jak.JavaClassDescriptor;
import net.dougqh.jak.JavaInterfaceDescriptor;

public interface JakTypeWriter {
	public abstract JakClassWriter define( final JavaClassDescriptor classBuilder );
	
	public abstract JakInterfaceWriter define( final JavaInterfaceDescriptor interfaceBuilder );
	
	public abstract JakAnnotationWriter define( final JavaAnnotationDescriptor annotationBuilder );
	
	public abstract Type thisType();
	
	public abstract Type superType();
}
