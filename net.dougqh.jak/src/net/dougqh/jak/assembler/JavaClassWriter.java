package net.dougqh.jak.assembler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;


public final class JavaClassWriter implements JavaExtendedTypeWriter {
	private static final int ADDITIONAL_TYPE_FLAGS = JavaFlagsBuilder.NO_FLAGS;
	private static final int ADDITIONAL_METHOD_FLAGS = JavaFlagsBuilder.NO_FLAGS;
	private static final int ADDITIONAL_INNER_TYPE_FLAGS = JavaFlagsBuilder.NO_FLAGS;
	
	private final TypeWriter typeWriter;
	
	JavaClassWriter(
		final TypeWriterGroup writerGroup,
		final TypeDescriptor typeDescriptor )
	{
		this.typeWriter = new TypeWriter(
			writerGroup,
			typeDescriptor,
			ADDITIONAL_TYPE_FLAGS );
	}
	
	public final void defineDefaultConstructor() {
		this.define( JavaAssembler.public_().init() ).
			this_().
			super_invokespecial( JavaAssembler.init() ).
			return_();
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
	
	public final JavaCodeWriter define(
		final JavaMethodDescriptor method )
	{
		return new JavaCodeWriter(
			this.typeWriter,
			method.isStatic(),
			method.arguments(),
			this.typeWriter.define(
				method,
				ADDITIONAL_METHOD_FLAGS,
				null ) );
	}
	
	public final JavaCodeWriter define( final Method method ) {
		return this.define( JavaAssembler.method( method ) );
	}
	
	@Override
	public final JavaClassWriter define(
		final JavaClassDescriptor classDescriptor )
	{
		return this.typeWriter.defineClass(
			classDescriptor.typeDescriptor(),
			ADDITIONAL_INNER_TYPE_FLAGS );
	}
	
	@Override
	public final JavaInterfaceWriter define(
		final JavaInterfaceDescriptor interfaceDescriptor )
	{
		return this.typeWriter.defineInterface(
			interfaceDescriptor.typeDescriptor(),
			ADDITIONAL_INNER_TYPE_FLAGS );
	}
	
	@Override
	public final JavaAnnotationWriter define(
		final JavaAnnotationDescriptor annotationDescriptor )
	{
		return this.typeWriter.defineAnnotation(
			annotationDescriptor.typeDescriptor(),
			ADDITIONAL_INNER_TYPE_FLAGS );
	}
	
	@Override
	public final Class< ? > load() {
		return this.typeWriter.load();
	}

	@Override
	public final Class< ? > load( final ClassLoader classLoader ) {
		return this.typeWriter.load( classLoader );
	}

	@SuppressWarnings( "unchecked" )
	public final < T > T newInstance() {
		try {
			return (T)this.load().newInstance();
		} catch ( InstantiationException e ) {
			throw new IllegalStateException( e );
		} catch ( IllegalAccessException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	@SuppressWarnings( "unchecked" )
	public final < T > T newInstance( final ClassLoader classLoader ) {
		try {
			return (T)this.load( classLoader ).newInstance();
		} catch ( InstantiationException e ) {
			throw new IllegalStateException( e );
		} catch ( IllegalAccessException e ) {
			throw new IllegalStateException( e );
		}
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
	
	public final void initConfig( final JakConfiguration config ) {
		this.typeWriter.initConfig( config );
	}
	
	final TypeWriter typeWriter() {
		return this.typeWriter;
	}
}
