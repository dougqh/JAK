package net.dougqh.jak;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.List;

import static net.dougqh.jak.Flags.*;
import static net.dougqh.jak.Methods.*;

public final class JavaModifiers {
	private static final TypeVariable<?>[] EMPTY_TYPE_VARS = {};
	
	private final int flags;
	private final TypeVariable<?>[] typeVars;
	
	JavaModifiers() {
		this( NO_FLAGS );
	}
	
	JavaModifiers( final int flag ) {
		this.flags = flag;
		this.typeVars = EMPTY_TYPE_VARS;
	}
	
	JavaModifiers(
		final JavaModifiers baseModifiers,
		final int flag )
	{
		if ( baseModifiers.typeVars.length != 0 ) {
			throw new IllegalStateException();
		}
		
		this.flags = baseModifiers.flags | flag;
		this.typeVars = EMPTY_TYPE_VARS;
	}
	
	JavaModifiers(
		final JavaModifiers baseModifiers,
		final TypeVariable<?>[] typeVars )
	{
		if ( baseModifiers.typeVars.length != 0 ) {
			throw new IllegalStateException();
		}
		
		this.flags = baseModifiers.flags;
		this.typeVars = typeVars;
	}
	
	public final JavaModifiers public_() {
		return new JavaModifiers( this, PUBLIC );
	}
	
	public final JavaModifiers protected_() {
		return new JavaModifiers( this, PROTECTED );
	}
	
	public final JavaModifiers private_() {
		return new JavaModifiers( this, PRIVATE );
	}
	
	public final JavaModifiers static_() {
		return new JavaModifiers( this, STATIC );
	}
	
	public final JavaModifiers abstract_() {
		return new JavaModifiers( this, ABSTRACT );
	}
	
	public final JavaModifiers final_() {
		return new JavaModifiers( this, FINAL );
	}
	
	public final JavaModifiers synchronized_() {
		return new JavaModifiers( this, SYNCHRONIZED );
	}
	
	public final JavaModifiers native_() {
		return new JavaModifiers( this, NATIVE );
	}
	
	public final JavaModifiers strictfp_() {
		return new JavaModifiers( this, STRICTFP );
	}
	
	public final JavaModifiers volatile_() {
		return new JavaModifiers( this, VOLATILE );
	}
	
	public final JavaModifiers transient_() {
		return new JavaModifiers( this, TRANSIENT );
	}
	
	public final JavaModifiers varargs() {
		return new JavaModifiers( this, VAR_ARGS );
	}
	
	public final JavaModifiers parameterize( final TypeVariable<?>... typeArgs ) {
		return new JavaModifiers( this, typeArgs );
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
		final Type arg1Type, final String arg1Name )
	{
		return this.method( returnType, methodName ).
			arg( arg1Type, arg1Name );
	}

	public final JavaMethodDescriptor method(
		final Type returnType,
		final String methodName,
		final Type arg1Type, final String arg1Name,
		final Type arg2Type, final String arg2Name )
	{
		return this.method( returnType, methodName ).
			arg( arg1Type, arg1Name ).
			arg( arg2Type, arg2Name );
	}

	public final JavaMethodDescriptor method(
		final Type returnType,
		final String methodName,
		final Type arg1Type, final String arg1Name,
		final Type arg2Type, final String arg2Name,
		final Type arg3Type, final String arg3Name )
	{
		return this.method( returnType, methodName ).
			arg( arg1Type, arg1Name ).
			arg( arg2Type, arg2Name ).
			arg( arg3Type, arg3Name );
	}
	
	public final JavaMethodDescriptor method(
		final Type returnType,
		final String methodName,
		final Type... args )
	{
		return this.method( returnType, methodName ).args( args );
	}
	
	public final JavaMethodDescriptor method(
		final Type returnType,
		final String methodName,
		final List<? extends Type> args )
	{
		return this.method( returnType, methodName ).args( args );
	}
	
	public final JavaMethodDescriptor init() {
		return new JavaMethodDescriptor( this, void.class, INIT );
	}
	
	public final JavaMethodDescriptor init( final Type... args ) {
		return this.init().args( args );
	}
	
	public final JavaClassDescriptor class_( final Package aPackage, final String className ) {
		return new JavaClassDescriptor( this, aPackage.getName() + "." + className );
	}
	
	public final JavaClassDescriptor class_( final String className ) {
		return new JavaClassDescriptor( this, className );
	}
	
	public final JavaInterfaceDescriptor interface_( final String interfaceName ) {
		return new JavaInterfaceDescriptor( this, interfaceName );
	}
	
	public final JavaInterfaceDescriptor interface_( final Package aPackage, final String interfaceName ) {
		return new JavaInterfaceDescriptor( this, aPackage.getName() + "." + interfaceName ); 
	}
	
	public final JavaEnumDescriptor enum_( final Package aPackage, final String className ) {
		return new JavaEnumDescriptor( this, aPackage.getName() + "." + className );
	}
	
	public final JavaEnumDescriptor enum_( final String className ) {
		return new JavaEnumDescriptor( this, className );
	}
	
	public final JavaAnnotationDescriptor $interface( final String annotationName ) {
		return new JavaAnnotationDescriptor( this, annotationName );
	}
	
	public final JavaAnnotationDescriptor annotation( final String annotationName ) {
		return $interface( annotationName );
	}
	
	public final JavaAnnotationDescriptor $interface( final Package aPackage, final String annotationName ) {
		return new JavaAnnotationDescriptor( this, aPackage.getName() + "." + annotationName );
	}
	
	public final JavaAnnotationDescriptor annotation( final Package aPackage, final String annotationName ) {
		return $interface( aPackage, annotationName );
	}
	
	final TypeVariable<?>[] typeVars() {
		return this.typeVars;
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
		} else if ( ! ( obj instanceof JavaModifiers ) ) {
			return false;
		} else {
			JavaModifiers that = (JavaModifiers)obj;
			return ( this.flags == that.flags ) &&
				Arrays.equals( this.typeVars, that.typeVars ); 
		}
	}
}
