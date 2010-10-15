package net.dougqh.jak.api;

import static net.dougqh.jak.JavaAssembler.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import net.dougqh.jak.JavaClassWriter;

import org.junit.Test;

public final class GenericsTest {
	public final @Test void genericReturnType() {
		JavaClassWriter classWriter = define( public_().final_().class_( "GenericReturnType" ) );
		
		classWriter.define( public_().init() ).
			this_().
			invokespecial( Object.class, init() ).
			return_();
		
		Type List_String = type( List.class ).of( String.class ).make();
		
		classWriter.define( public_().final_().method( List_String, "getStrings" ) ).
			aconst_null().
			areturn();
		
		Class< ? > generatedClass = classWriter.load();
		Method method = getMethod( generatedClass, "getStrings" );
		
		assertEquals(
			List.class,
			method.getReturnType() );
		assertEquals(
			type( List.class ).of( String.class ).make(),
			method.getGenericReturnType() );
	}
	
	public final @Test void wildcardExtendsType() {
		JavaClassWriter classWriter = define( public_().final_().class_( "WilcardExtends" ) );
		
		classWriter.define( public_().init() ).
			this_().
			invokespecial( Object.class, init() ).
			return_();
		
		Type List_extends_String = 
			type( List.class ).of(
				type().extends_( String.class ) ).make();
		
		classWriter.define( public_().final_().method( List_extends_String, "getStrings" ) ).
			aconst_null().
			areturn();
		
		Class< ? > generatedClass = classWriter.load();
		Method method = getMethod( generatedClass, "getStrings" );
		
		assertEquals(
			type( List.class ).of( type().extends_( String.class ) ).make(),
			method.getGenericReturnType() );
	}
	
	public final @Test void genericParameterType() {
		JavaClassWriter classWriter = define( public_().final_().class_( "GenericReturnType" ) );
		
		classWriter.define( public_().init() ).
			this_().
			invokespecial( Object.class, init() ).
			return_();
		
		Type List_String = type( List.class ).of( String.class ).make();
		
		classWriter.define( public_().final_().method( void.class, "setStrings", List_String ) ).
			aconst_null().
			return_();
		
		Class< ? > generatedClass = classWriter.load();
		Method method = getMethod( generatedClass, "setStrings" );
		
		Class< ? >[] paramClasses = method.getParameterTypes();
		Type[] paramTypes = method.getGenericParameterTypes();
		
		assertEquals( 1, paramClasses.length );
		assertEquals( 1, paramTypes.length );
		
		assertEquals( List.class, paramClasses[ 0 ] );
		assertEquals(
			type( List.class ).of( String.class ).make(),
			paramTypes[ 0 ] );
	}
	
	@SuppressWarnings( "unchecked" )
	public final @Test void genericCode() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "GenericCode" ).implements_( Creator.class ) );
		
		classWriter.define( public_().init() ).
			this_().
			invokespecial( Object.class, init() ).
			return_();
		
		Type ArrayList_String = type( ArrayList.class ).of( String.class ).make();
		
		classWriter.define( public_().final_().method( Object.class, "create" ) ).
			new_( ArrayList_String ).
			dup().
			invokespecial( ArrayList_String, init() ).
			areturn();
		
		Creator creator = classWriter.< Creator >newInstance();
		assertTrue( creator.create() instanceof List );
	}
	
	public final @Test void genericField() {
		JavaClassWriter classWriter = define( public_().final_().class_( "GenericField" ) );

		Type List_String = type( List.class ).of( String.class ).make();
		
		classWriter.define(
			public_().static_().field( List_String, "strings" ) );
		
		Field field = getField( classWriter.load(), "strings" );
		
		assertEquals(
			type( List.class ).of( String.class ).make(),
			field.getGenericType() );
	}
	
	public final @Test void genericExtension() {
		JavaClassWriter classWriter = define(
			public_().abstract_().class_( "GenericExtension" ).
				extends_( type( AbstractList.class ).of( String.class ) ).
				implements_( type( List.class ).of( String.class ) ) );
		
		Class< ? > aClass = classWriter.load();
		
		assertEquals(
			type( AbstractList.class ).of( String.class ).make(),
			aClass.getGenericSuperclass() );
		
		Type[] interfaceTypes = aClass.getGenericInterfaces();
		assertEquals( 1, interfaceTypes.length );
		assertEquals(
			type( List.class ).of( String.class ).make(),
			interfaceTypes[ 0 ] );
	}
	
	private static final Field getField(
		final Class< ? > aClass,
		final String fieldName )
	{
		try {
			return aClass.getField( fieldName );
		} catch ( SecurityException e ) {
			throw new IllegalStateException( e );
		} catch ( NoSuchFieldException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	private static final Method getMethod(
		final Class< ? > aClass,
		final String methodName )
	{
		for ( Method method: aClass.getDeclaredMethods() ) {
			if ( method.getName().equals( methodName ) ) {
				return method;
			}
		}
		throw new IllegalStateException( "Method " + methodName + " not found" );
	}
	
	public interface Creator {
		public abstract Object create();
	}
}
