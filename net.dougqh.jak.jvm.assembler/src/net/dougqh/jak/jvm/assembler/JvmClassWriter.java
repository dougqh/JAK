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
import net.dougqh.jak.assembler.JakClassWriter;
import net.dougqh.jak.assembler.TypeResolver;

public final class JvmClassWriter
	implements JakClassWriter, JvmExtendedTypeWriter
{
	private static final int ADDITIONAL_TYPE_FLAGS = Flags.NO_FLAGS;
	private static final int ADDITIONAL_METHOD_FLAGS = Flags.NO_FLAGS;
	private static final int ADDITIONAL_INNER_TYPE_FLAGS = Flags.NO_FLAGS;
	
	private final TypeWriter typeWriter;
	
	JvmClassWriter(
		final TypeWriterGroup writerGroup,
		final TypeDescriptor typeDescriptor )
	{
		this.typeWriter = new TypeWriter(
			writerGroup,
			typeDescriptor,
			ADDITIONAL_TYPE_FLAGS );
	}
	
	public final void defineDefaultConstructor() {
		this.define( Jak.public_().init() ).
			this_().
			super_invokespecial( Jak.init() ).
			return_();
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
	
	public final JvmCodeWriter define(
		final JavaMethodDescriptor method )
	{
		//coreWriter will be null when the method is abstract or native pass it on
		JvmCoreCodeWriter coreWriter = this.typeWriter.define(
			method,
			ADDITIONAL_METHOD_FLAGS,
			null );
		
		if ( coreWriter == null ) {
			return null;
		} else {
			return new JvmCodeWriterImpl(
				this.typeWriter,
				method.isStatic(),
				method.arguments(),
				coreWriter );
		}
	}
	
	public final JvmCodeWriter define( final Method method ) {
		return this.define( Jak.method( method ) );
	}
	
	@Override
	public final JvmClassWriter define(
		final JavaClassDescriptor classDescriptor )
	{
		return this.typeWriter.defineClass(
			classDescriptor.typeDescriptor(),
			ADDITIONAL_INNER_TYPE_FLAGS );
	}
	
	@Override
	public final JvmInterfaceWriter define(
		final JavaInterfaceDescriptor interfaceDescriptor )
	{
		return this.typeWriter.defineInterface(
			interfaceDescriptor.typeDescriptor(),
			ADDITIONAL_INNER_TYPE_FLAGS );
	}
	
	@Override
	public final JvmAnnotationWriter define(
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
	
	@Override
	public final void writeTo( final File classDir ) throws IOException {
		this.typeWriter.writeTo( classDir );
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
