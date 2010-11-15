package net.dougqh.jak.assembler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;

public interface JavaTypeWriter {
	public abstract JavaClassWriter define( final JavaClassDescriptor classBuilder );
	
	public abstract JavaInterfaceWriter define( final JavaInterfaceDescriptor interfaceBuilder );
	
	public abstract JavaAnnotationWriter define( final JavaAnnotationDescriptor annotationBuilder );
	
	public abstract Type thisType();
	
	public abstract Type superType();
	
	public abstract Class< ? > load();
	
	public abstract Class< ? > load( final ClassLoader classLoader );
	
	public abstract void writeTo( final File srcDir ) throws IOException;
	
	public abstract void writeTo( final OutputStream out ) throws IOException;
	
	public abstract byte[] getBytes();
}
