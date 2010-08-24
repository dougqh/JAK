package net.dougqh.jak.api;

import static net.dougqh.jak.JavaAssembler.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import net.dougqh.jak.JavaClassWriter;

import org.junit.Test;

public final class CodeGenFieldsTest {
	public @Test final void staticFields() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "StaticFields" ).implements_( SetGet.class ) );
		
		Type thisType = classWriter.thisType();
		
		classWriter.define( private_().static_().field( String.class, "message" ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().final_().method( void.class, "set", String.class ) ).
			aload_1().
			putstatic( thisType, field( String.class, "message" ) ).
			return_();
		
		classWriter.define( public_().final_().method( String.class, "get" ) ).
			getstatic( thisType, field( String.class, "message" ) ).
			areturn();
		
		SetGet testObject = classWriter.< SetGet >newInstance();
		testObject.set( "Hello World" );
		assertEquals( "Hello World", testObject.get() );
	}
	
	public @Test final void staticFieldsWithConstantValue() {
		JavaClassWriter classWriter = define( public_().final_().class_( "StaticConstants" ) );
		
		classWriter.define(
			public_().static_().final_().field( boolean.class, "BOOLEAN" ),
			true );
		
		classWriter.define(
			public_().static_().final_().field( byte.class, "BYTE" ),
			(byte)-10 );
		
		classWriter.define(
			public_().static_().final_().field( char.class, "CHAR" ),
			'a' );
		
		classWriter.define(
			public_().static_().final_().field( short.class, "SHORT" ),
			(short)10 );
		
		classWriter.define(
			public_().static_().final_().field( int.class, "INT" ),
			20 );
		
		classWriter.define(
			public_().static_().final_().field( long.class, "LONG" ),
			30L );
		
		classWriter.define(
			public_().static_().final_().field( float.class, "FLOAT" ),
			40F );
		
		classWriter.define(
			public_().static_().final_().field( double.class, "DOUBLE" ),
			50D );
		
		classWriter.define(
			public_().static_().final_().field( String.class, "STRING" ),
			"Hello World" );
		
		Class< ? > aClass = classWriter.load();
		
		assertEquals( Boolean.TRUE, getFieldValue( aClass, "BOOLEAN" ) );
		assertEquals( (byte)-10, getFieldValue( aClass, "BYTE" ) );
		assertEquals( 'a', getFieldValue( aClass, "CHAR" ) );
		assertEquals( (short)10, getFieldValue( aClass, "SHORT" ) );
		assertEquals( 20, getFieldValue( aClass, "INT" ) );
		assertEquals( 30L, getFieldValue( aClass, "LONG" ) );
		assertEquals( 40F, getFieldValue( aClass, "FLOAT" ) );
		assertEquals( 50D, getFieldValue( aClass, "DOUBLE" ) );
		assertEquals( "Hello World", getFieldValue( aClass, "STRING" ) );
	}
	
	public @Test final void staticFieldsWithConstantValueFixType() {
		JavaClassWriter classWriter = define( public_().final_().class_( "StaticConstants" ) );
		
		classWriter.define(
			public_().static_().final_().field( boolean.class, "BOOLEAN" ),
			true );
		
		classWriter.define(
			public_().static_().final_().field( byte.class, "BYTE" ),
			-10 );
		
		classWriter.define(
			public_().static_().final_().field( char.class, "CHAR" ),
			'a' );
		
		classWriter.define(
			public_().static_().final_().field( short.class, "SHORT" ),
			10 );
		
		classWriter.define(
			public_().static_().final_().field( int.class, "INT" ),
			20 );
		
		classWriter.define(
			public_().static_().final_().field( long.class, "LONG" ),
			30 );
		
		classWriter.define(
			public_().static_().final_().field( float.class, "FLOAT" ),
			40 );
		
		classWriter.define(
			public_().static_().final_().field( double.class, "DOUBLE" ),
			50 );
		
		classWriter.define(
			public_().static_().final_().field( String.class, "STRING" ),
			"Hello World" );
		
		Class< ? > aClass = classWriter.load();
		
		assertEquals( Boolean.TRUE, getFieldValue( aClass, "BOOLEAN" ) );
		assertEquals( (byte)-10, getFieldValue( aClass, "BYTE" ) );
		assertEquals( 'a', getFieldValue( aClass, "CHAR" ) );
		assertEquals( (short)10, getFieldValue( aClass, "SHORT" ) );
		assertEquals( 20, getFieldValue( aClass, "INT" ) );
		assertEquals( 30L, getFieldValue( aClass, "LONG" ) );
		assertEquals( 40F, getFieldValue( aClass, "FLOAT" ) );
		assertEquals( 50D, getFieldValue( aClass, "DOUBLE" ) );
		assertEquals( "Hello World", getFieldValue( aClass, "STRING" ) );
	}	
	
	private static final Object getFieldValue(
		final Class< ? > aClass,
		final String fieldName )
	{
		try {
			Field field = aClass.getField( fieldName );
			return field.get( null );
		} catch ( SecurityException e ) {
			throw new IllegalStateException( e );
		} catch ( NoSuchFieldException e ) {
			throw new IllegalStateException( e );
		} catch ( IllegalArgumentException e ) {
			throw new IllegalStateException( e );
		} catch ( IllegalAccessException e ) {
			throw new IllegalStateException( e );
		}
	}

	public @Test final void instanceFields() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "InstanceFields" ).implements_( SetGet.class ) );
		
		Type thisType = classWriter.thisType();
		
		classWriter.define( private_().field( String.class, "message" ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().final_().method( void.class, "set", String.class ) ).
			this_().
			aload_1().
			putfield( thisType, field( String.class, "message" ) ).
			return_();
		
		classWriter.define( public_().final_().method( String.class, "get" ) ).
			this_().
			getfield( thisType, field( String.class, "message" ) ).
			areturn();
		
		SetGet testObject = classWriter.< SetGet >newInstance();
		testObject.set( "Hello World" );
		assertEquals( "Hello World", testObject.get() );
	}
	
	public static interface SetGet {
		public abstract void set( final String in );
		
		public abstract String get();
	}
}
