package net.dougqh.jak;

import static net.dougqh.jak.Flags.*;
import static net.dougqh.jak.Methods.*;

import java.lang.reflect.Type;

import net.dougqh.java.meta.types.JavaTypeBuilder;

public final class JavaFlagsBuilder {
	private final int flags;
	
	JavaFlagsBuilder() {
		this( NO_FLAGS );
	}
	
	JavaFlagsBuilder( final int flag ) {
		this.flags = flag;
	}
	
	JavaFlagsBuilder(
		final JavaFlagsBuilder baseBuilder,
		final int flag )
	{
		this.flags = baseBuilder.flags | flag;
	}
	
	public final JavaFlagsBuilder public_() {
		return new JavaFlagsBuilder( this, PUBLIC );
	}
	
	public final JavaFlagsBuilder protected_() {
		return new JavaFlagsBuilder( this, PROTECTED );
	}
	
	public final JavaFlagsBuilder private_() {
		return new JavaFlagsBuilder( this, PRIVATE );
	}
	
	public final JavaFlagsBuilder static_() {
		return new JavaFlagsBuilder( this, STATIC );
	}
	
	public final JavaFlagsBuilder abstract_() {
		return new JavaFlagsBuilder( this, ABSTRACT );
	}
	
	public final JavaFlagsBuilder final_() {
		return new JavaFlagsBuilder( this, FINAL );
	}
	
	public final JavaFlagsBuilder synchronized_() {
		return new JavaFlagsBuilder( this, SYNCHRONIZED );
	}
	
	public final JavaFlagsBuilder native_() {
		return new JavaFlagsBuilder( this, NATIVE );
	}
	
	public final JavaFlagsBuilder strictfp_() {
		return new JavaFlagsBuilder( this, STRICTFP );
	}
	
	public final JavaFlagsBuilder volatile_() {
		return new JavaFlagsBuilder( this, VOLATILE );
	}
	
	public final JavaFlagsBuilder transient_() {
		return new JavaFlagsBuilder( this, TRANSIENT );
	}
	
	public final JavaFlagsBuilder varargs() {
		return new JavaFlagsBuilder( this, VAR_ARGS );
	}
	
	public final JavaFieldImpl field(
		final Type fieldType,
		final CharSequence fieldName )
	{
		return new JavaFieldImpl(
			this,
			fieldType,
			fieldName );
	}
	
	public final JavaFieldImpl field(
		final JavaTypeBuilder typeBuilder,
		final CharSequence fieldName )
	{
		return this.field( typeBuilder.make(), fieldName );
	}

	public final JavaMethodDescriptor method(
		final Type returnType,
		final String methodName )
	{
		return new JavaMethodDescriptor(
			this,
			returnType,
			methodName );
	}
	
	public final JavaMethodDescriptor method(
		final Type returnType,
		final String methodName,
		final Type... args )
	{
		return this.method( returnType, methodName ).args( args );
	}
	
	public final JavaMethodDescriptor init() {
		return new JavaMethodDescriptor(
			this,
			void.class,
			INIT );
	}
	
	public final JavaMethodDescriptor init( final Type... args ) {
		return this.init().args( args );
	}
	
	public final JavaClassDescriptor class_(
		final Package aPackage,
		final String className )
	{
		return new JavaClassDescriptor(
			this,
			aPackage.getName() + "." + className );
	}
	
	public final JavaClassDescriptor class_(
		final String className )
	{
		return new JavaClassDescriptor( this, className );
	}
	
	public final JavaInterfaceDescriptor interface_(
		final String interfaceName )
	{
		return new JavaInterfaceDescriptor( this, interfaceName );
	}
	
	public final JavaInterfaceDescriptor interface_(
		final Package aPackage,
		final String interfaceName )
	{
		return new JavaInterfaceDescriptor(
			this,
			aPackage.getName() + "." + interfaceName ); 
	}
	
	public final JavaAnnotationDescriptor $interface(
		final String annotationName )
	{
		return new JavaAnnotationDescriptor( this, annotationName );
	}
	
	public final JavaAnnotationDescriptor $interface(
		final Package aPackage,
		final String annotationName )
	{
		return new JavaAnnotationDescriptor(
			this,
			aPackage.getName() + "." + annotationName );
	}
	
	final int flags() {
		return this.flags;
	}
	
	@Override
	public final int hashCode() {
		return this.flags;
	}
	
	@Override
	public final boolean equals( final Object obj ) {
		if ( obj == this ) {
			return true;
		} else if ( ! ( obj instanceof JavaFlagsBuilder ) ) {
			return false;
		} else {
			JavaFlagsBuilder that = (JavaFlagsBuilder)obj;
			return ( this.flags == that.flags );
		}
	}
}
