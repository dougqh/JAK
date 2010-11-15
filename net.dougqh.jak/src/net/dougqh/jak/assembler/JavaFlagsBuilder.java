package net.dougqh.jak.assembler;

import java.lang.reflect.Type;

import net.dougqh.java.meta.types.JavaTypeBuilder;

public final class JavaFlagsBuilder {
	static final int NO_FLAGS = 0x0;
	
	static final int PUBLIC = 0x0001;
	static final int PRIVATE = 0x0002;
	static final int PROTECTED = 0x0004;
	
	static final int STATIC = 0x0008;
	static final int FINAL = 0x0010;

	static final int VOLATILE = 0x0040;
	static final int TRANSIENT = 0x0080;
	static final int VAR_ARGS = 0x0080;
	static final int NATIVE = 0x0100;
	static final int ABSTRACT = 0x0400;
	static final int STRICTFP = 0x0800;

	static final int SYNCHRONIZED = 0x0020;
	
	static final int SUPER = 0x0020;
	static final int INTERFACE = 0x0200;
	static final int ANNOTATION = 0x2000;
	static final int ENUM = 0x4000;
	
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
	
	public final JavaFieldDescriptor field(
		final Type fieldType,
		final CharSequence fieldName )
	{
		return new JavaFieldDescriptor(
			this,
			fieldType,
			fieldName );
	}
	
	public final JavaFieldDescriptor field(
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
			JavaMethodDescriptor.INIT );
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
	
	static final boolean isAbstract( final int flags ) {
		return ( ( flags & ABSTRACT ) != 0 );
	}
	
	static final boolean isNative( final int flags ) {
		return ( ( flags & NATIVE ) != 0 );
	}
	
	static final boolean isStatic( final int flags ) {
		return ( ( flags & STATIC ) != 0 );
	}
}
