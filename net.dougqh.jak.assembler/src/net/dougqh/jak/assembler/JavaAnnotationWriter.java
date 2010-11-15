package net.dougqh.jak.assembler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;

public final class JavaAnnotationWriter implements JavaTypeWriter {
	private static final int ADDITIONAL_TYPE_FLAGS = 
		JavaFlagsBuilder.ABSTRACT | JavaFlagsBuilder.INTERFACE | JavaFlagsBuilder.ANNOTATION;

	private static final int ADDITIONAL_METHOD_FLAGS = 
		JavaFlagsBuilder.PUBLIC | JavaFlagsBuilder.ABSTRACT;
	
	private static final int ADDITIONAL_INNER_TYPE_FLAGS = 
		JavaFlagsBuilder.PUBLIC | JavaFlagsBuilder.STATIC;
	
	private final TypeWriter typeWriter;
	
	JavaAnnotationWriter(
		final TypeWriterGroup typeWriterGroup,
		final TypeDescriptor typeDescriptor )
	{
		this.typeWriter = new TypeWriter(
			typeWriterGroup,
			typeDescriptor,
			ADDITIONAL_TYPE_FLAGS );
	}
	
	public final void define( final JavaFieldDescriptor field ) {
		this.typeWriter.define(
			JavaAssembler.method( field.getType(), field.getName() ),
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
	
	public final void define( final JavaFieldDescriptor field, final boolean value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaFieldDescriptor field, final byte value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaFieldDescriptor field, final char value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaFieldDescriptor field, final short value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaFieldDescriptor field, final int value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaFieldDescriptor field, final long value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaFieldDescriptor field, final float value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaFieldDescriptor field, final double value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaFieldDescriptor field, final CharSequence value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaFieldDescriptor field, final Class< ? > value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaFieldDescriptor field, final Enum< ? > value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaFieldDescriptor field, final boolean[] value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaFieldDescriptor field, final byte[] value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaFieldDescriptor field, final char[] value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaFieldDescriptor field, final short[] value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaFieldDescriptor field, final int[] value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaFieldDescriptor field, final long[] value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaFieldDescriptor field, final float[] value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaFieldDescriptor field, final double[] value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaFieldDescriptor field, final CharSequence[] value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaFieldDescriptor field, final Class< ? >[] value ) {
		this.typeWriter.define( field, value );
	}
	
	public final void define( final JavaFieldDescriptor field, final Enum< ? >[] value ) {
		this.typeWriter.define( field, value );
	}

	public final void define( final JavaFieldDescriptor field, final Object value ) {
		this.typeWriter.define( field, value );
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
