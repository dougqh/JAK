package net.dougqh.jak.jvm.assembler.api;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmInterfaceWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;
import net.dougqh.java.meta.types.type;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Test;

import static net.dougqh.jak.Jak.*;
import static org.junit.Assert.*;

public final class GenericsTest {
	@Test
	public final void genericMethod() throws NoSuchMethodException {
		JvmClassWriter classWriter = new JvmWriter().define( public_().class_( "GenericMethod" ) );
		
		classWriter.define( public_().abstract_().parameterize( T ).method( T, "get", parameterize( Class.class ).of( T ) ) );
		
		Class<?> parameterized = classWriter.load();
		
		Method method = parameterized.getMethod( "get", Class.class );
		assertThat( method.getReturnType(), is( Object.class ) );
		assertThat( method.getParameterTypes()[ 0 ], is( Class.class ) );
		
		TypeVariable<?> returnType = (TypeVariable<?>)method.getGenericReturnType();
		assertThat( returnType.getName(), is( "T" ) );
		
		ParameterizedType parameterType = (ParameterizedType)method.getGenericParameterTypes()[ 0 ];
		assertThat( parameterType.getRawType(), is( Class.class ) );

		TypeVariable<?> typeVar = (TypeVariable<?>)parameterType.getActualTypeArguments()[ 0 ];
		assertThat( typeVar.getName(), is( "T" ) );
	}
	
	@Test
	public final void genericExtendsMethod() throws NoSuchMethodException {
		JvmClassWriter classWriter = new JvmWriter().define( public_().class_( "GenericMethod" ) );
		
		classWriter.define(
			public_().abstract_().parameterize( T.extends_( Number.class ) ).method( T, "get", parameterize( Class.class ).of( T ) ) );
		
		Class<?> parameterized = classWriter.load();
		
		Method method = parameterized.getMethod( "get", Class.class );
		assertThat( method.getReturnType(), is( Number.class ) );
		assertThat( method.getParameterTypes()[ 0 ], is( Class.class ) );
		
		TypeVariable<?> returnType = (TypeVariable<?>)method.getGenericReturnType();
		assertThat( returnType.getName(), is( "T" ) );
		assertThat( returnType.getBounds()[0], is( Number.class ) );
		
		ParameterizedType parameterType = (ParameterizedType)method.getGenericParameterTypes()[ 0 ];
		assertThat( parameterType.getRawType(), is( Class.class ) );

		TypeVariable<?> typeVar = (TypeVariable<?>)parameterType.getActualTypeArguments()[ 0 ];
		assertThat( typeVar.getName(), is( "T" ) );
	}
	
	@Test
	public final void genericClass() throws NoSuchFieldException, NoSuchMethodException {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "GenericClass" ).parameterize( T.extends_( Number.class ) ) );
		
		classWriter.define( public_().field( T, "field" ) );
		
		classWriter.define( public_().abstract_().method( T, "method" ) );
		
		Class<?> parameterized = classWriter.load();
		
		Field field = parameterized.getField( "field" );
		assertThat( field.getType(), is( Number.class ) );
		
		Method method = parameterized.getMethod( "method" );
		assertThat( method.getReturnType(), is( Number.class ) );
	}
	
	@Test
	public final void genericInterface() throws NoSuchFieldException, NoSuchMethodException {
		JvmInterfaceWriter interfaceWriter = new JvmWriter().define(
			public_().interface_( "GenericInterface" ).parameterize( V.extends_( Number.class ) ) );
		
		interfaceWriter.define( public_().abstract_().method( V, "method" ) );
		
		Class<?> parameterized = interfaceWriter.load();
		
		Method method = parameterized.getMethod( "method" );
		assertThat( method.getReturnType(), is( Number.class ) );
	}
	
	@Test
	public final void specificReturnType() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "SpecificReturnType" ) );
		
		classWriter.defineDefaultConstructor();
		
		Type List_String = parameterize( List.class ).of( String.class );
		
		classWriter.define( public_().final_().method( List_String, "getStrings" ) ).
			aconst_null().
			areturn();
		
		Class< ? > generatedClass = classWriter.load();
		Method method = getMethod( generatedClass, "getStrings" );
		
		assertEquals( List.class, method.getReturnType() );
		assertEquals(
			new type< List< String > >() {}.get(),
			method.getGenericReturnType() );
	}
	
	@Test
	public final void wildcardExtendsType() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "WilcardExtends" ) );
		
		classWriter.defineDefaultConstructor();
		
		Type List_extends_String = new type< List< ? extends String > >() {}.get();
		
		classWriter.define( public_().final_().method( List_extends_String, "getStrings" ) ).
			aconst_null().
			areturn();
		
		Class< ? > generatedClass = classWriter.load();
		Method method = getMethod( generatedClass, "getStrings" );
		
		assertEquals(
			parameterize( List.class ).of( $.extends_( String.class ) ),
			method.getGenericReturnType() );
	}
	
	@Test
	public final void specificParameterType() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "SpecificReturnType" ) );
		
		classWriter.defineDefaultConstructor();
		
		Type List_String = parameterize( List.class ).of( String.class );
		
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
			parameterize( List.class ).of( String.class ),
			paramTypes[ 0 ] );
	}
	
	@Test
	public final void specificCode() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "SpecificCode" ).implements_( Creator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		Type ArrayList_String = parameterize( ArrayList.class ).of( String.class );
		
		classWriter.define( public_().final_().method( Object.class, "create" ) ).
			new_( ArrayList_String ).
			dup().
			invokespecial( ArrayList_String, init() ).
			areturn();
		
		Creator creator = classWriter.< Creator >newInstance();
		assertTrue( creator.create() instanceof List );
	}
	
	@Test
	public final void specificField() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "SpecificField" ) );

		Type List_String = new type< List< String > >() {};
		
		classWriter.define(
			public_().static_().field( List_String, "strings" ) );
		
		Field field = getField( classWriter.load(), "strings" );
		
		assertEquals(
			parameterize( List.class ).of( String.class ),
			field.getGenericType() );
	}
	
	@Test
	public final void specificExtension() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().abstract_().class_( "SpecificExtension" ).
				extends_( parameterize( AbstractList.class ).of( String.class ) ).
				implements_( parameterize( List.class ).of( String.class ) ) );
		
		Class< ? > aClass = classWriter.load();
		
		assertEquals(
			parameterize( AbstractList.class ).of( String.class ),
			aClass.getGenericSuperclass() );
		
		Type[] interfaceTypes = aClass.getGenericInterfaces();
		assertEquals( 1, interfaceTypes.length );
		assertEquals(
			parameterize( List.class ).of( String.class ),
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
	
	private static final Matcher< String > is( final String value ) {
		return CoreMatchers.is( value );
	}
	
	private static final Matcher< Type > is( final Type type ) {
		return CoreMatchers.is( type );
	}
	
	public interface Creator {
		public abstract Object create();
	}
}
