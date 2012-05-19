package net.dougqh.jak;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import javax.lang.model.element.TypeElement;

import net.dougqh.java.meta.types.JavaParameterizedType;
import net.dougqh.java.meta.types.JavaTypeVariable;
import net.dougqh.java.meta.types.JavaTypes;
import net.dougqh.java.meta.types.JavaWildcardType;
import static net.dougqh.jak.Methods.*;

public class Jak {
	public static final Type void_ = void.class;
	public static final Type boolean_ = boolean.class;
	public static final Type byte_ = byte.class;
	public static final Type char_ = char.class;
	public static final Type short_ = short.class;
	public static final Type int_ = int.class;
	public static final Type long_ = long.class;
	public static final Type float_ = float.class;
	public static final Type double_ = double.class;
	
	public static final JavaWildcardType $ = wildcard();
	public static final JavaTypeVariable A = typeVar( "A" );
	public static final JavaTypeVariable B = typeVar( "B" );
	public static final JavaTypeVariable C = typeVar( "C" );
	public static final JavaTypeVariable D = typeVar( "D" );
	public static final JavaTypeVariable E = typeVar( "E" );
	public static final JavaTypeVariable F = typeVar( "F" );
	public static final JavaTypeVariable G = typeVar( "G" );
	public static final JavaTypeVariable H = typeVar( "H" );
	public static final JavaTypeVariable I = typeVar( "I" );
	public static final JavaTypeVariable J = typeVar( "J" );
	public static final JavaTypeVariable K = typeVar( "K" );
	public static final JavaTypeVariable L = typeVar( "L" );
	public static final JavaTypeVariable M = typeVar( "M" );
	public static final JavaTypeVariable N = typeVar( "N" );
	public static final JavaTypeVariable O = typeVar( "O" );
	public static final JavaTypeVariable P = typeVar( "P" );
	public static final JavaTypeVariable Q = typeVar( "Q" );
	public static final JavaTypeVariable R = typeVar( "R" );
	public static final JavaTypeVariable S = typeVar( "S" );
	public static final JavaTypeVariable T = typeVar( "T" );
	public static final JavaTypeVariable U = typeVar( "U" );
	public static final JavaTypeVariable V = typeVar( "V" );
	public static final JavaTypeVariable W = typeVar( "W" );
	public static final JavaTypeVariable X = typeVar( "X" );
	public static final JavaTypeVariable Y = typeVar( "Y" );
	public static final JavaTypeVariable Z = typeVar( "Z" );
	
	public static final JavaPackageDescriptor package_( final String name ) {
		return new JavaPackageDescriptor( name );
	}
	
	public static final JavaModifiers public_() {
		return new JavaModifiers( Flags.PUBLIC );
	}
	
	public static final JavaModifiers protected_() {
		return new JavaModifiers( Flags.PROTECTED );
	}
	
	public static final JavaModifiers private_() {
		return new JavaModifiers( Flags.PRIVATE );
	}
	
	public static final JavaModifiers abstract_() {
		return new JavaModifiers( Flags.ABSTRACT );
	}
	
	public static final JavaModifiers static_() {
		return new JavaModifiers( Flags.STATIC );
	}
	
	public static final JavaModifiers final_() {
		return new JavaModifiers( Flags.FINAL );
	}
	
	public static final JavaModifiers synchronized_() {
		return new JavaModifiers( Flags.SYNCHRONIZED );
	}
	
	public static final JavaModifiers native_() {
		return new JavaModifiers( Flags.NATIVE );
	}
	
	public static final JavaModifiers strictfp_() {
		return new JavaModifiers( Flags.STRICTFP );
	}
	
	public static final JavaModifiers volatile_() {
		return new JavaModifiers( Flags.VOLATILE );
	}
	
	public static final JavaModifiers transient_() {
		return new JavaModifiers( Flags.TRANSIENT );
	}
	
	public static final JavaModifiers varargs() {
		return new JavaModifiers( Flags.VAR_ARGS );
	}
	
	public static final JavaClassDescriptor class_( final String className ) {
		return new JavaModifiers().class_( className );
	}
	
	public static final JavaClassDescriptor class_(
		final Package aPackage,
		final String className )
	{
		return new JavaModifiers().class_( aPackage, className );
	}
	
	public static final JavaInterfaceDescriptor interface_(
		final String interfaceName )
	{
		return new JavaModifiers().interface_( interfaceName );
	}
	
	public static final JavaInterfaceDescriptor interface_(
		final Package aPackage,
		final String interfaceName )
	{
		return new JavaModifiers().interface_( aPackage, interfaceName );
	}
	
	public static final JavaField field( final Field field ) {
		return new JavaModifiers( field.getModifiers() ).field(
			field.getGenericType(),
			field.getName() );
	}
	
	public static final JavaField field(
		final Type fieldType,
		final CharSequence fieldName )
	{
		return new JavaModifiers().field( fieldType, fieldName );
	}
	
	public static final JavaMethodDescriptor method(
		final Method method )
	{
		JavaMethodDescriptor methodDescriptor = new JavaMethodDescriptor(
			new JavaModifiers( method.getModifiers() ),
			method.getGenericReturnType(),
			method.getName() );
		methodDescriptor.args( method.getGenericParameterTypes() );
		methodDescriptor.throws_( method.getGenericExceptionTypes() );
		
		return methodDescriptor;
	}
	
	public static final JavaMethodDescriptor method(
		final Type returnType,
		final String methodName )
	{
		return new JavaModifiers().method( returnType, methodName );
	}
	
	public static final JavaMethodDescriptor method(
		final Type returnType,
		final String methodName,
		final Type... args )
	{
		return new JavaModifiers().method( returnType, methodName, args );
	}
	
	public static final JavaMethodDescriptor method(
		final Type returnType, 
		final String methodName,
		final Type arg1Type, final String arg1Name )
	{
		return new JavaModifiers().method( returnType, methodName ).
			arg( arg1Type, arg1Name );
	}

	public static final JavaMethodDescriptor method(
		final Type returnType,
		final String methodName,
		final Type arg1Type, final String arg1Name,
		final Type arg2Type, final String arg2Name )
	{
		return new JavaModifiers().method( returnType, methodName ).
			arg( arg1Type, arg1Name ).
			arg( arg2Type, arg2Name );
	}

	public static final JavaMethodDescriptor method(
		final Type returnType,
		final String methodName,
		final Type arg1Type, final String arg1Name,
		final Type arg2Type, final String arg2Name,
		final Type arg3Type, final String arg3Name )
	{
		return new JavaModifiers().method( returnType, methodName ).
			arg( arg1Type, arg1Name ).
			arg( arg2Type, arg2Name ).
			arg( arg3Type, arg3Name );
	}	
	
	public static final JavaMethodSignature method(
		final String methodName )
	{
		return new JavaMethodSignature( methodName );
	}
	
	public static final JavaMethodSignature method(
		final String methodName,
		final Type... args )
	{
		return method( methodName ).args( args );
	}
	
	public static final JavaMethodDescriptor init() {
		return new JavaModifiers().init();
	}
	
	public static final JavaMethodDescriptor init( final Type... args ) {
		return new JavaModifiers().init( args );
	}
	
	public static final JavaMethodDescriptor clinit() {
		return static_().method( void.class, CLINIT ); 
	}
	
	//Methods from JavaTypes
	private static final JavaWildcardType wildcard() {
		return new JavaWildcardType();
	}
	
	public static final Type type( final TypeElement typeElement ) {
		return JavaTypes.type( typeElement );
	}
	
	public static final Type objectTypeName( final String name ) {
		return JavaTypes.objectTypeName( name );
	}
	
	public static final JavaTypeVariable typeVar( final String name ) {
		return JavaTypes.typeVar( name );
	}
	
	public static final JavaParameterizedType parameterize( final Type rawType ) {
		return JavaTypes.parameterize( rawType );
	}
	
	public static final Type array( final Type type ) {
		return JavaTypes.array( type );
	}
}
