package net.dougqh.jak.jvm.assembler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;

import net.dougqh.jak.Flags;
import net.dougqh.jak.Jak;
import net.dougqh.jak.JavaAnnotationDescriptor;
import net.dougqh.jak.JavaClassDescriptor;
import net.dougqh.jak.JavaField;
import net.dougqh.jak.JavaInterfaceDescriptor;
import net.dougqh.jak.TypeDescriptor;
import net.dougqh.jak.assembler.JakAnnotationWriter;

public final class JvmAnnotationWriter
	implements JakAnnotationWriter, JvmTypeWriter
{
	private static final int ADDITIONAL_TYPE_FLAGS = 
		Flags.ABSTRACT | Flags.INTERFACE | Flags.ANNOTATION;

	private static final int ADDITIONAL_METHOD_FLAGS = 
		Flags.PUBLIC | Flags.ABSTRACT;
	
	private static final int ADDITIONAL_INNER_TYPE_FLAGS = 
		Flags.PUBLIC | Flags.STATIC;
	
	private final TypeWriter typeWriter;
	
	JvmAnnotationWriter(
		final TypeWriterGroup typeWriterGroup,
		final TypeDescriptor typeDescriptor )
	{
		this.typeWriter = new TypeWriter(
			typeWriterGroup,
			typeDescriptor,
			ADDITIONAL_TYPE_FLAGS );
	}
	
	public final void define( final JavaField field ) {
		this.typeWriter.define(
			Jak.method( field.getType(), field.getName() ),
			ADDITIONAL_METHOD_FLAGS,
			null );
	}
	
	@Override
	public final Type thisType() {
		return this.typeWriter.thisType();
	}
	
	@Override
	public final Type superType() {
		return this.typeWriter.superType();
	}
	
	public final void define( final JavaField field, final boolean value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaField field, final byte value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaField field, final char value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaField field, final short value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaField field, final int value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaField field, final long value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaField field, final float value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaField field, final double value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaField field, final CharSequence value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaField field, final Class< ? > value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaField field, final Enum< ? > value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaField field, final boolean[] value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaField field, final byte[] value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaField field, final char[] value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaField field, final short[] value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaField field, final int[] value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaField field, final long[] value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaField field, final float[] value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaField field, final double[] value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaField field, final CharSequence[] value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaField field, final Class< ? >[] value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaField field, final Enum< ? >[] value ) {
		this.typeWriter.define( field, value );
	}

	public final void define( final JavaField field, final Object value ) {
		this.typeWriter.define( field, value );
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
