package net.dougqh.jak.jvm.assembler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import net.dougqh.jak.Flags;
import net.dougqh.jak.Jak;
import net.dougqh.jak.JavaAnnotationDescriptor;
import net.dougqh.jak.JavaClassDescriptor;
import net.dougqh.jak.JavaField;
import net.dougqh.jak.JavaInterfaceDescriptor;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.TypeDescriptor;
import net.dougqh.jak.assembler.JakInterfaceWriter;


public final class JvmInterfaceWriter
	implements JakInterfaceWriter, JvmExtendedTypeWriter
{
	private static final int ADDITIONAL_TYPE_FLAGS = 
		Flags.ABSTRACT | Flags.INTERFACE;
	
	private static final int ADDITIONAL_METHOD_FLAGS = 
		Flags.PUBLIC | Flags.ABSTRACT;
	
	private static final int ADDITIONAL_INNER_TYPE_FLAGS = 
		Flags.PUBLIC | Flags.STATIC;	
	
	private final TypeWriter typeWriter;
	
	public JvmInterfaceWriter(
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
		return this.typeWriter.context().thisType;
	}
	
	@Override
	public final Type superType() {
		return this.typeWriter.context().superType;
	}
	
	@Override
	public final void define( final JavaField field ) {
		this.typeWriter.define( field );
	}
	
	@Override
	public final void define(
		final JavaField field,
		final boolean value )
	{
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define(
		final JavaField field,
		final byte value )
	{
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define(
		final JavaField field,
		final char value )
	{
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define(
		final JavaField field,
		final short value )
	{
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define(
		final JavaField field,
		final int value )
	{
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define(
		final JavaField field,
		final long value )
	{
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define(
		final JavaField field,
		final float value )
	{
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define(
		final JavaField field,
		final double value )
	{
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define(
		final JavaField field,
		final CharSequence value )
	{
		this.typeWriter.define( field, value );
	}
	
	@Override
	public final void define(
		final JavaField field,
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
		this.define( Jak.method( method ) );
	}
	
	@Override
	public final JvmClassWriter define(
		final JavaClassDescriptor classBuilder )
	{
		return this.typeWriter.defineClass(
			classBuilder.typeDescriptor(),
			ADDITIONAL_INNER_TYPE_FLAGS );
	}
	
	@Override
	public final JvmInterfaceWriter define(
		final JavaInterfaceDescriptor interfaceBuilder )
	{
		return this.typeWriter.defineInterface(
			interfaceBuilder.typeDescriptor(),
			ADDITIONAL_INNER_TYPE_FLAGS );
	}
	
	@Override
	public final JvmAnnotationWriter define(
		final JavaAnnotationDescriptor annotationBuilder )
	{
		return this.typeWriter.defineAnnotation(
			annotationBuilder.typeDescriptor(),
			ADDITIONAL_INNER_TYPE_FLAGS );
	}
	
	@Override
	public final <T> Class<T> load() {
		return this.typeWriter.<T>load();
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
