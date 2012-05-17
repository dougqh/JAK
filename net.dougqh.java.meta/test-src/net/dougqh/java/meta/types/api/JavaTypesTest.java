package net.dougqh.java.meta.types.api;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.dougqh.java.meta.types.type;

import org.junit.Test;

import static net.dougqh.java.meta.types.JavaTypes.*;
import static org.junit.Assert.*;

public final class JavaTypesTest {
	@Test
	public final void rawTypes() {
		assertEquals(
			boolean.class,
			getRawClass( boolean.class ) );
		assertEquals(
			List.class,
			getRawClass( List.class ) );
		assertEquals(
			Map.class,
			getRawClass( parameterize( Map.class ).of( String.class, String.class ) ) );
		assertEquals(
			List.class,
			getRawClass( parameterize( List.class ).of( wildcard().extends_( Number.class ) ) ) );
		assertEquals(
			Set.class,
			getRawClass( parameterize( Set.class ).of( wildcard().super_( Number.class ) ) ) );
		assertEquals(
			Runnable.class,
			getRawClass( wildcard().extends_( Runnable.class ) ) );
		assertEquals(
			Object.class,
			getRawClass( wildcard().super_( Runnable.class ) ) );
	}
	
	@Test
	public final void equals() {
		assertEquals(
			new type< List< String > >() {}.get(),
			parameterize( List.class ).of( String.class ) );
		assertEquals(
			new type< Map< String, Integer > >() {}.get(),
			parameterize( Map.class ).of( String.class, Integer.class ) );
		assertEquals(
			new type< List< ? extends String > >() {}.get(),
			parameterize( List.class ).of( wildcard().extends_( String.class ) ) );
		assertEquals(
			new type< List< ? super String > >() {}.get(),
			parameterize( List.class ).of( wildcard().super_( String.class  ) ) );
	}
	
	@Test
	public final void booleans() {
		assertEquals( boolean.class, getPrimitiveType( Boolean.class ) );
		assertEquals( boolean.class, getPrimitiveType( boolean.class ) );
		assertEquals( Boolean.class, getObjectType( boolean.class ) );
		assertEquals( Boolean.class, getObjectType( Boolean.class ) );
	}
	
	@Test
	public final void chars() {
		assertEquals( char.class, getPrimitiveType( Character.class ) );
		assertEquals( char.class, getPrimitiveType( char.class ) );
		assertEquals( Character.class, getObjectType( char.class ) );
		assertEquals( Character.class, getObjectType( Character.class ) );
	}
	
	@Test
	public final void shorts() {
		assertEquals( short.class, getPrimitiveType( Short.class ) );
		assertEquals( short.class, getPrimitiveType( short.class ) );
		assertEquals( Short.class, getObjectType( short.class ) );
		assertEquals( Short.class, getObjectType( Short.class ) );
	}
	
	@Test
	public final void ints() {
		assertEquals( int.class, getPrimitiveType( Integer.class ) );
		assertEquals( int.class, getPrimitiveType( int.class ) );
		assertEquals( Integer.class, getObjectType( int.class ) );
		assertEquals( Integer.class, getObjectType( Integer.class ) );
	}
	
	@Test
	public final void longs() {
		assertEquals( long.class, getPrimitiveType( Long.class ) );
		assertEquals( long.class, getPrimitiveType( long.class ) );
		assertEquals( Long.class, getObjectType( long.class ) );
		assertEquals( Long.class, getObjectType( Long.class ) );
	}
	
	@Test
	public final void floats() {
		assertEquals( float.class, getPrimitiveType( Float.class ) );
		assertEquals( float.class, getPrimitiveType( float.class ) );
		assertEquals( Float.class, getObjectType( float.class ) );
		assertEquals( Float.class, getObjectType( Float.class ) );
	}
	
	@Test
	public final void doubles() {
		assertEquals( double.class, getPrimitiveType( Double.class ) );
		assertEquals( double.class, getPrimitiveType( double.class ) );
		assertEquals( Double.class, getObjectType( double.class ) );
		assertEquals( Double.class, getObjectType( Double.class ) );
	}
	
	@Test
	public final void voids() {
		assertEquals( void.class, getPrimitiveType( Void.class ) );
		assertEquals( void.class, getPrimitiveType( void.class ) );
		assertEquals( Void.class, getObjectType( void.class ) );
		assertEquals( Void.class, getObjectType( Void.class ) );
		
	}
	
	@Test
	public final void objects() {
		assertNull( getPrimitiveType( String.class ) );
		assertEquals( String.class, getObjectType( String.class ) );
	}
}
