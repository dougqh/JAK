package net.dougqh.jak.jvm.assembler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import net.dougqh.jak.JavaAnnotationDescriptor;
import net.dougqh.jak.JavaClassDescriptor;
import net.dougqh.jak.JavaInterfaceDescriptor;
import net.dougqh.jak.assembler.JakTypeWriter;

public interface JvmTypeWriter extends JakTypeWriter {
	public abstract JvmClassWriter define( final JavaClassDescriptor classBuilder );
	
	public abstract JvmInterfaceWriter define( final JavaInterfaceDescriptor interfaceBuilder );
	
	public abstract JvmAnnotationWriter define( final JavaAnnotationDescriptor annotationBuilder );

	public abstract <T> Class<T> load();
	
	public abstract void writeTo( final File srcDir ) throws IOException;
	
	public abstract void writeTo( final OutputStream out ) throws IOException;
	
	public abstract byte[] getBytes();
}
