package net.dougqh.jak.api;

import static net.dougqh.jak.JavaAssembler.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import net.dougqh.jak.JavaClassWriter;

import org.junit.Test;

public final class ModifiersTest {
	public final @Test void fieldModifiers() {
		JavaClassWriter classWriter = define( public_().final_().class_( "Fields" ) );
		
		classWriter.define(
			public_().static_().final_().field( int.class, "STATIC" ),
			42 );
		
		classWriter.define(
			private_().transient_().field( int.class, "number" ) );
		
		classWriter.define(
			protected_().volatile_().field( int.class, "atomic" ) );
		
		classWriter.defineDefaultConstructor();
		
		Class< ? > aClass = classWriter.load();
		
		Field staticField = getField( aClass, "STATIC" );
		assertTrue( Modifier.isPublic( staticField.getModifiers() ) );
		assertTrue( Modifier.isStatic( staticField.getModifiers() ) );
		assertTrue( Modifier.isFinal( staticField.getModifiers() ) );
		
		Field numberField = getField( aClass, "number" );
		assertTrue( Modifier.isPrivate( numberField.getModifiers() ) );
		assertTrue( Modifier.isTransient( numberField.getModifiers() ) );
		
		Field atomicField = getField( aClass, "atomic" );
		assertTrue( Modifier.isProtected( atomicField.getModifiers() ) );
		assertTrue( Modifier.isVolatile( atomicField.getModifiers() ) );
	}
	
	private static final Field getField(
		final Class< ? > aClass,
		final String fieldName )
	{
		try {
			return aClass.getDeclaredField( fieldName );
		} catch ( NoSuchFieldException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	public final @Test void methodModifiers() {
		JavaClassWriter classWriter = define( public_().abstract_().class_( "Methods" ) );
		
		classWriter.define( public_().final_().strictfp_().method( void.class, "method1" ) ).
			return_();
		
		classWriter.define( protected_().abstract_().method( void.class, "method2" ) );
		
		classWriter.define( private_().native_().method( void.class, "method3" ) );
		
		classWriter.define( synchronized_().varargs().method( void.class, "method4", String[].class ) ).
			return_();
		
		Class< ? > aClass = classWriter.load();
		
		Method method1 = getMethod( aClass, "method1" );
		assertTrue( Modifier.isPublic( method1.getModifiers() ) );
		assertTrue( Modifier.isFinal( method1.getModifiers() ) );
		assertTrue( Modifier.isStrict( method1.getModifiers() ) );
		
		Method method2 = getMethod( aClass, "method2" );
		assertTrue( Modifier.isProtected( method2.getModifiers() ) );
		assertTrue( Modifier.isAbstract( method2.getModifiers() ) );
		
		Method method3 = getMethod( aClass, "method3" );
		assertTrue( Modifier.isPrivate( method3.getModifiers() ) );
		assertTrue( Modifier.isNative( method3.getModifiers() ) );
		
		Method method4 = getMethod( aClass, "method4", String[].class );
		assertTrue( Modifier.isSynchronized( method4.getModifiers() ) );
		assertTrue( method4.isVarArgs() );
	}
	
	private static final Method getMethod(
		final Class< ? > aClass,
		final String methodName,
		final Class< ? >... paramClasses )
	{
		try {
			return aClass.getDeclaredMethod( methodName, paramClasses );
		} catch ( NoSuchMethodException e ) {
			throw new IllegalStateException( e );
		}
	}
}
