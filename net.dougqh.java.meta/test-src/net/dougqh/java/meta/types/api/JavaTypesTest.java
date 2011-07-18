package net.dougqh.java.meta.types.api;

import static net.dougqh.java.meta.types.JavaTypes.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public final class JavaTypesTest {
	public final @Test void upperBounds() {
		assertEquals(
			boolean.class,
			getRawClass( boolean.class ) );
		assertEquals(
			List.class,
			getRawClass( List.class ) );
		assertEquals(
			Map.class,
			getRawClass( type( Map.class ).of( String.class, String.class ).make() ) );
		assertEquals(
			List.class,
			getRawClass( type( List.class ).of( type().extends_( Number.class ) ).make() ) );
		assertEquals(
			Set.class,
			getRawClass( type( Set.class ).of( type().super_( Number.class ) ).make() ) );
		assertEquals(
			Runnable.class,
			getRawClass( type().extends_( Runnable.class ).make() ) );
		assertEquals(
			Object.class,
			getRawClass( type().super_( Runnable.class ).make() ) );
//		assertEquals(
//			Runnable.class,
//			upperBound( type( "T" ).extends_( Runnable.class ) ) );
	}
	
	public final @Test void booleans() {
		assertEquals( boolean.class, getPrimitiveType( Boolean.class ) );
		assertEquals( boolean.class, getPrimitiveType( boolean.class ) );
		assertEquals( Boolean.class, getObjectType( boolean.class ) );
		assertEquals( Boolean.class, getObjectType( Boolean.class ) );
	}
	
	public final @Test void chars() {
		assertEquals( char.class, getPrimitiveType( Character.class ) );
		assertEquals( char.class, getPrimitiveType( char.class ) );
		assertEquals( Character.class, getObjectType( char.class ) );
		assertEquals( Character.class, getObjectType( Character.class ) );
	}
	
	public final @Test void shorts() {
		assertEquals( short.class, getPrimitiveType( Short.class ) );
		assertEquals( short.class, getPrimitiveType( short.class ) );
		assertEquals( Short.class, getObjectType( short.class ) );
		assertEquals( Short.class, getObjectType( Short.class ) );
	}
	
	public final @Test void ints() {
		assertEquals( int.class, getPrimitiveType( Integer.class ) );
		assertEquals( int.class, getPrimitiveType( int.class ) );
		assertEquals( Integer.class, getObjectType( int.class ) );
		assertEquals( Integer.class, getObjectType( Integer.class ) );
	}
	
	public final @Test void longs() {
		assertEquals( long.class, getPrimitiveType( Long.class ) );
		assertEquals( long.class, getPrimitiveType( long.class ) );
		assertEquals( Long.class, getObjectType( long.class ) );
		assertEquals( Long.class, getObjectType( Long.class ) );
	}
	
	public final @Test void floats() {
		assertEquals( float.class, getPrimitiveType( Float.class ) );
		assertEquals( float.class, getPrimitiveType( float.class ) );
		assertEquals( Float.class, getObjectType( float.class ) );
		assertEquals( Float.class, getObjectType( Float.class ) );
	}
	
	public final @Test void doubles() {
		assertEquals( double.class, getPrimitiveType( Double.class ) );
		assertEquals( double.class, getPrimitiveType( double.class ) );
		assertEquals( Double.class, getObjectType( double.class ) );
		assertEquals( Double.class, getObjectType( Double.class ) );
	}
	
	public final @Test void voids() {
		assertEquals( void.class, getPrimitiveType( Void.class ) );
		assertEquals( void.class, getPrimitiveType( void.class ) );
		assertEquals( Void.class, getObjectType( void.class ) );
		assertEquals( Void.class, getObjectType( Void.class ) );
		
	}
	
	public final @Test void objects() {
		assertNull( getPrimitiveType( String.class ) );
		assertEquals( String.class, getObjectType( String.class ) );
	}
}
