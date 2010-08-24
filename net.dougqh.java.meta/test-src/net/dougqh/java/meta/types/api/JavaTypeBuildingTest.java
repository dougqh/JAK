package net.dougqh.java.meta.types.api;


import static net.dougqh.java.meta.types.JavaTypes.*;
import static org.junit.Assert.*;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public final class JavaTypeBuildingTest {
	public final @Test void nameLookup() {
		assertEquals( Integer.class, objectTypeName( "java.lang.Integer" ) );
	}
	
	public final @Test void basic() {
		assertEquals( Object.class, type( Object.class ).make() );
		assertEquals( String.class, type( String.class ).make() );
	}
	
	public final @Test void basicArray() {
		assertEquals( Object[].class, array( Object.class ) );
		assertEquals( int[].class, array( int.class ) );
	}
	
	public final @Test void parameterizedNoWildcards() {
		assertEquals(
			returnTypeOf( "map_String_String" ),
			type( Map.class ).of( String.class, String.class ).make() );
	}
	
	public final @Test void parameterizeExtendsWildcard() {
		assertEquals(
			returnTypeOf( "list_extends_Number" ),
			type( List.class ).of( type().extends_( Number.class ) ).make() );
	}
	
	public final @Test void parameterizedSuperWildcard() {
		assertEquals(
			returnTypeOf( "set_super_Integer" ),
			type( Set.class ).of( type().super_( Integer.class ) ).make() );
	}
	
	//DQH - Since the generated type is not tied back to a source
	//element (in this case a method), equals will fail.
	//Instead compare the components that should match.
	@SuppressWarnings( "unchecked" )
	public final @Test void classTypeVariable() {
		TypeVariable expected = (TypeVariable)returnTypeOf( "t" );
		TypeVariable actual = (TypeVariable)typeVar( "T" ).make();
		
		assertEquals( expected.getName(), actual.getName() );
		assertArrayEquals( expected.getBounds(), actual.getBounds() );
	}
	
	@SuppressWarnings( "unchecked" )
	public final @Test void methodTypeVariable() {
		TypeVariable expected = (TypeVariable)returnTypeOf( "u" );
		TypeVariable actual = (TypeVariable)typeVar( "U" ).make();
		
		assertEquals( expected.getName(), actual.getName() );
		assertArrayEquals( expected.getBounds(), actual.getBounds() );
	}
	
	@SuppressWarnings( "unchecked" )
	public final @Test void genericArray() {
		GenericArrayType expected = (GenericArrayType)returnTypeOf( "ts" );
		GenericArrayType actual = (GenericArrayType)array( typeVar( "T" ).make() );
		
		TypeVariable expectedComponentType = (TypeVariable)expected.getGenericComponentType();
		TypeVariable actualComponentType = (TypeVariable)actual.getGenericComponentType();

		assertEquals( expectedComponentType.getName(), actualComponentType.getName() );
		assertArrayEquals( expectedComponentType.getBounds(), actualComponentType.getBounds() );
	}
	
	private final Type returnTypeOf( final String name ) {
		for( Method method : Signatures.class.getDeclaredMethods() ) {
			if ( method.getName().equals( name ) ) {
				return method.getGenericReturnType();
			}
		}
		throw new IllegalStateException( "Unknown method: " + name );
	}
	
	public interface Signatures< T > {
		public abstract Map< String, String > map_String_String();
		
		public abstract List< ? extends Number > list_extends_Number();
		
		public abstract Set< ? super Integer > set_super_Integer();
		
		public abstract T t();
		
		public abstract < U > U u();
		
		public abstract T[] ts();
	}
}
