package net.dougqh.jak.assembler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public final class JavaInterfaceWriter implements JavaExtendedTypeWriter {
	private static final int ADDITIONAL_TYPE_FLAGS = 
		JavaFlagsBuilder.ABSTRACT | JavaFlagsBuilder.INTERFACE;
	
	private static final int ADDITIONAL_METHOD_FLAGS = 
		JavaFlagsBuilder.PUBLIC | JavaFlagsBuilder.ABSTRACT;
	
	private static final int ADDITIONAL_INNER_TYPE_FLAGS = 
		JavaFlagsBuilder.PUBLIC | JavaFlagsBuilder.STATIC;	
	
	private final TypeWriter typeWriter;
	
	public JavaInterfaceWriter(
		final TypeWriterGroup writerGroup,
		final TypeDescriptor typeDescriptor )
	{
		this.typeWriter = new TypeWriter(
			writerGroup, 
			typeDescriptor,
			ADDITIONAL_TYPE_FLAGS );
	}
	
	@Override
	public final Type thisType() {
		return this.typeWriter.thisType();
	}
	
	@Override
	public final Type superType() {
		return this.typeWriter.superType();
	}
	
	@Override
	public final void define( final JavaFieldDescriptor field ) {
		this.typeWriter.define( field );
	}
	
	@Override
	public final void define(
		final JavaFieldDescriptor field,
		final boolean value )
	{
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define(
		final JavaFieldDescriptor field,
		final byte value )
	{
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define(
		final JavaFieldDescriptor field,
		final char value )
	{
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define(
		final JavaFieldDescriptor field,
		final short value )
	{
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define(
		final JavaFieldDescriptor field,
		final int value )
	{
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define(
		final JavaFieldDescriptor field,
		final long value )
	{
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define(
		final JavaFieldDescriptor field,
		final float value )
	{
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define(
		final JavaFieldDescriptor field,
		final double value )
	{
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define(
		final JavaFieldDescriptor field,
		final CharSequence value )
	{
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define(
		final JavaFieldDescriptor field,
		final Class< ? > aClass )
	{
		this.typeWriter.define( field, aClass );
	}
	
	public final void define(
		final JavaMethodDescriptor methodBuilder )
	{
		this.typeWriter.define(
			methodBuilder,
			ADDITIONAL_METHOD_FLAGS,
			null );
	}
	
	public final void define( final Method method ) {
		this.define( JavaAssembler.method( method ) );
	}
	
	@Override
	public final JavaClassWriter define(
		final JavaClassDescriptor classBuilder )
	{
		return this.typeWriter.defineClass(
			classBuilder.typeDescriptor(),
			ADDITIONAL_INNER_TYPE_FLAGS );
	}
	
	@Override
	public final JavaInterfaceWriter define(
		final JavaInterfaceDescriptor interfaceBuilder )
	{
		return this.typeWriter.defineInterface(
			interfaceBuilder.typeDescriptor(),
			ADDITIONAL_INNER_TYPE_FLAGS );
	}
	
	@Override
	public final JavaAnnotationWriter define(
		final JavaAnnotationDescriptor annotationBuilder )
	{
		return this.typeWriter.defineAnnotation(
			annotationBuilder.typeDescriptor(),
			ADDITIONAL_INNER_TYPE_FLAGS );
	}
	
	@Override
	public final Class< ? > load() {
		return this.typeWriter.load();
	}
	
	@Override
	public final void writeTo( final File srcDir ) throws IOException {
		this.typeWriter.writeTo( srcDir );
	}
	
	@Override
	public final void writeTo( final OutputStream out ) throws IOException {
		this.typeWriter.writeTo( out );
	}
	
	@Override
	public final byte[] getBytes() {
		return this.typeWriter.getBytes();
	}	
	
	final TypeWriter typeWriter() {
		return this.typeWriter;
	}
}
