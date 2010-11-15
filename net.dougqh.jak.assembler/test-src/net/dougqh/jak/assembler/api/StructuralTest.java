package net.dougqh.jak.assembler.api;

import static net.dougqh.jak.assembler.JavaAssembler.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.dougqh.jak.assembler.JavaClassWriter;
import net.dougqh.jak.assembler.JavaInterfaceWriter;
import net.dougqh.jak.assembler.JavaWriter;

import org.junit.Test;

public final class StructuralTest {
	private static final ClassLoader CLASS_LOADER = StructuralTest.class.getClassLoader();
	
	public final @Test void trivialClass() {
		JavaClassWriter classWriter = new JavaWriter().define(
			public_().final_().class_( "TrivialClass" ) );
		Class< ? > newClass = classWriter.load();
		
		assertEquals( "TrivialClass", newClass.getSimpleName() );
		assertFalse( newClass.isInterface() );
	}

	public final @Test void trivialInterface() {
		JavaInterfaceWriter interfaceWriter = new JavaWriter().define(
			public_().interface_( "TrivialInterface" ) );
		Class< ? > newInterface = interfaceWriter.load();
		
		assertEquals( "TrivialInterface", newInterface.getName() );
		assertTrue( newInterface.isInterface() );
	}
	
	public final @Test void interfaceExtends() {
		JavaInterfaceWriter interfaceWriter = new JavaWriter().define(
			public_().interface_( "SerializableRunnable" ).
				extends_( Serializable.class, Runnable.class ) );
		
		Class< ? > newInterface = interfaceWriter.load();
		assertEquals( "SerializableRunnable", newInterface.getName() );
		assertTrue( newInterface.isInterface() );
		assertArrayEquals(
			new Class[] { Serializable.class, Runnable.class },
			newInterface.getInterfaces() );
	}
	
	public final @Test void throwsException() {
		JavaInterfaceWriter interfaceWriter = new JavaWriter().define(
			public_().interface_( "ThrowsException" ) );
			
		interfaceWriter.define( method( void.class, "run" ).throws_( Exception.class ) );
		
		Class< ? > testClass = interfaceWriter.load();
		Method runMethod = getMethod( testClass, "run" );
		
		assertArrayEquals(
			new Class[] { Exception.class },
			runMethod.getExceptionTypes() );
	}
	
	public final @Test void runnableImpl() {
		JavaClassWriter classWriter = new JavaWriter().define(
			public_().abstract_().class_( "RunnableImpl" ).
				extends_( Object.class ).
				implements_( Runnable.class ) );
		
		Class< ? > generatedClass = classWriter.load();
		assertTrue(
			"generatedClass instanceof Runnable",
			Runnable.class.isAssignableFrom( generatedClass ) );
	}
	
	public final @Test void nontrivialInterface() {
		TestClassLoader classLoader = new TestClassLoader( CLASS_LOADER, "Interface" ) {
			@Override
			protected final byte[] generateClass() throws IOException {
				JavaInterfaceWriter writer = new JavaWriter().define(
					public_().interface_( this.className ) );
				
				writer.define(
					method( boolean.class, "getBoolean" ) );
				writer.define(
					method( void.class, "setBoolean" ).args( boolean.class ) );
				
				writer.define(
					method( byte.class, "getByte" ) );
				writer.define(
					method( void.class, "setByte" ).args( byte.class ) );
				
				writer.define(
					method( char.class, "getChar" ) );
				writer.define(
					method( void.class, "setChar" ).args( char.class ) );
				
				writer.define(
					method( short.class, "getShort" ) );
				writer.define(
					method( void.class, "setShort" ).args( short.class ) );
				
				writer.define(
					method( int.class, "getInt" ) );
				writer.define(
					method( void.class, "setInt" ).args( int.class ) );
				
				writer.define(
					method( long.class, "getLong" ) );
				writer.define(
					method( void.class, "setLong" ).args( long.class ) );
				
				writer.define(
					method( float.class, "getFloat" ) );
				writer.define(
					method( void.class, "setFloat" ).args( float.class ) );
				
				writer.define(
					method( double.class, "getDouble" ) );
				writer.define(
					method( void.class, "setDouble" ).args( double.class ) );
				
				writer.define(
					method( String.class, "getString" ) );
				writer.define(
					method( void.class, "setString" ).args( String.class ) );
				
				return writer.getBytes();
			}
		};
		
		Class< ? > anInterface = classLoader.loadClass();
		assertTrue( "interface?", anInterface.isInterface() );
		
		assertEquals(
			boolean.class,
			getMethod( anInterface, "getBoolean" ).getReturnType() );
		assertEquals(
			void.class,
			getMethod( anInterface, "setBoolean", boolean.class ).getReturnType() );
		
		assertEquals(
			byte.class,
			getMethod( anInterface, "getByte" ).getReturnType() );
		assertEquals(
			void.class,
			getMethod( anInterface, "setByte", byte.class ).getReturnType() );
		
		assertEquals(
			char.class,
			getMethod( anInterface, "getChar" ).getReturnType() );
		assertEquals(
			void.class,
			getMethod( anInterface, "setChar", char.class ).getReturnType() );

		assertEquals(
			short.class,
			getMethod( anInterface, "getShort" ).getReturnType() );
		assertEquals(
			void.class,
			getMethod( anInterface, "setShort", short.class ).getReturnType() );
		
		assertEquals(
			int.class,
			getMethod( anInterface, "getInt" ).getReturnType() );
		assertEquals(
			void.class,
			getMethod( anInterface, "setInt", int.class ).getReturnType() );

		assertEquals(
			long.class,
			getMethod( anInterface, "getLong" ).getReturnType() );
		assertEquals(
			void.class,
			getMethod( anInterface, "setLong", long.class ).getReturnType() );

		assertEquals(
			float.class,
			getMethod( anInterface, "getFloat" ).getReturnType() );
		assertEquals(
			void.class,
			getMethod( anInterface, "setFloat", float.class ).getReturnType() );
	
		assertEquals(
			double.class,
			getMethod( anInterface, "getDouble" ).getReturnType() );
		assertEquals(
			void.class,
			getMethod( anInterface, "setDouble", double.class ).getReturnType() );

		assertEquals(
			String.class,
			getMethod( anInterface, "getString" ).getReturnType() );
		assertEquals(
			void.class,
			getMethod( anInterface, "setString", String.class ).getReturnType() );
	}
	
	public final @Test void staticFields() {
		TestClassLoader classLoader = new TestClassLoader( CLASS_LOADER, "Fields" ) {
			@Override
			protected final byte[] generateClass() throws IOException {
				JavaClassWriter writer = new JavaWriter().define(
					public_().final_().class_( this.className ) );
				
				writer.define(
					public_().static_().final_().field( boolean.class, "booleanField" ),
					true );
				
				writer.define(
					public_().static_().final_().field( char.class, "charField" ),
					'a' );
				
				writer.define(
					public_().static_().final_().field( byte.class, "byteField" ),
					Byte.MAX_VALUE );

				writer.define(
					public_().static_().final_().field( short.class, "shortField" ),
					Short.MAX_VALUE );

				writer.define(
					public_().static_().final_().field( int.class, "intField" ),
					Integer.MAX_VALUE );
				
				writer.define(
					public_().static_().final_().field( long.class, "longField" ),
					Long.MAX_VALUE );
				
				writer.define(
					public_().static_().final_().field( float.class, "floatField" ),
					Float.NaN );
				
				writer.define(
					public_().static_().final_().field( double.class, "doubleField" ),
					Double.POSITIVE_INFINITY );
				
				writer.define(
					public_().static_().final_().field( String.class, "stringField" ),
					"Hello World" );
					
				return writer.getBytes();
			}
		};
		
		Class< ? > fieldsClass = classLoader.loadClass();
		
		assertEquals( true, getStaticField( fieldsClass, "booleanField" ) );
		assertEquals( 'a', getStaticField( fieldsClass, "charField" ) );
		assertEquals( Byte.MAX_VALUE, getStaticField( fieldsClass, "byteField" ) );
		assertEquals( Short.MAX_VALUE, getStaticField( fieldsClass, "shortField" ) );
		assertEquals( Integer.MAX_VALUE, getStaticField( fieldsClass, "intField" ) );
		assertEquals( Long.MAX_VALUE, getStaticField( fieldsClass, "longField" ) );
		assertEquals( Float.NaN, getStaticField( fieldsClass, "floatField" ) );
		assertEquals( Double.POSITIVE_INFINITY, getStaticField( fieldsClass, "doubleField" ) );
		assertEquals( "Hello World", getStaticField( fieldsClass, "stringField" ) );
	}
	
	public final @Test void runnable() {
		JavaClassWriter classWriter = new JavaWriter().define(
			public_().final_().class_( "RunnableImpl" ).implements_( Runnable.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().final_().method( void.class, "run" ) ).
			return_();
		
		Runnable runnable = classWriter.< Runnable >newInstance();
		runnable.run();
	}
	
	public final @Test void helloWorldRunnable() {
		JavaClassWriter classWriter = new JavaWriter().define(
			public_().final_().class_( "RunnableImpl" ).implements_( Runnable.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().final_().method( void.class, "run" ).throws_( Exception.class ) ).
			getstatic( System.class, "out" ).
			ldc( "Hello World" ).
			invoke( PrintStream.class, method( "println" ).args( String.class ) ).
			return_();
		
		Runnable runnable = classWriter.< Runnable >newInstance();
		runnable.run();
	}
	
	public final @Test void innerClass() {
		JavaClassWriter outerClassWriter = new JavaWriter().define(
			public_().final_().class_( "Outer" ) );
		
		JavaClassWriter innerClassWriter = 
			outerClassWriter.define( public_().static_().final_().class_( "Inner" ) );
		
		Class< ? > outerClass = outerClassWriter.load();
		Class< ? > innerClass = innerClassWriter.load();

		assertArrayEquals(
			new Class[] { innerClass },
			outerClass.getDeclaredClasses() );
		
		assertEquals(
			outerClass,
			innerClass.getDeclaringClass() );
	}
	
	public final @Test void innerInterface() {
		JavaClassWriter outerClassWriter = new JavaWriter().define(
			public_().final_().class_( "foo.bar.Outer" ) );
		
		JavaInterfaceWriter innerClassWriter = 
			outerClassWriter.define( public_().static_().interface_( "Inner" ) );
		
		Class< ? > outerClass = outerClassWriter.load();
		Class< ? > innerClass = innerClassWriter.load();

		assertArrayEquals(
			new Class[] { innerClass },
			outerClass.getDeclaredClasses() );
		
		assertEquals(
			outerClass,
			innerClass.getDeclaringClass() );		
	}
	
	private static final Object getStaticField(
		final Class< ? > aClass,
		final String fieldName )
	{
		try {
			Field field = aClass.getField( fieldName );
			return field.get( null );
		} catch ( NoSuchFieldException e ) {
			throw new IllegalStateException( e );
		} catch ( IllegalAccessException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	private static final Method getMethod(
		final Class< ? > aClass,
		final String name,
		final Class< ? >... argTypes )
	{
		try {
			return aClass.getDeclaredMethod( name, argTypes );
		} catch ( NoSuchMethodException e ) {
			throw new IllegalStateException( e );
		}
	}
}
