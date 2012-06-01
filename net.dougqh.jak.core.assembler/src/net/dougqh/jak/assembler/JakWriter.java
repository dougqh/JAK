package net.dougqh.jak.assembler;

import net.dougqh.jak.JavaAnnotationDescriptor;
import net.dougqh.jak.JavaClassDescriptor;
import net.dougqh.jak.JavaEnumDescriptor;
import net.dougqh.jak.JavaInterfaceDescriptor;

public interface JakWriter {
	public abstract JakClassWriter define( final JavaClassDescriptor descriptor );
	
	public abstract JakInterfaceWriter define( final JavaInterfaceDescriptor descriptor );
	
	public abstract JakEnumWriter define( final JavaEnumDescriptor descriptor );
	
	public abstract JakAnnotationWriter define( final JavaAnnotationDescriptor descriptor );
}
