package net.dougqh.java.meta.types.api;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import net.dougqh.java.meta.types.JavaTypes;

import org.junit.Test;

public final class JavaJvmTypesTest {
	public @Test void fromPersistedNamePrimitives() {
		assertEquals( void.class, JavaTypes.fromPersistedName( "V" ) );
		assertEquals( boolean.class, JavaTypes.fromPersistedName( "Z" ) );
		assertEquals( byte.class, JavaTypes.fromPersistedName( "B" ) );
		assertEquals( char.class, JavaTypes.fromPersistedName( "C" ) );
		assertEquals( short.class, JavaTypes.fromPersistedName( "S" ) );
		assertEquals( int.class, JavaTypes.fromPersistedName( "I" ) );
		assertEquals( long.class, JavaTypes.fromPersistedName( "J" ) );
		assertEquals( float.class, JavaTypes.fromPersistedName( "F" ) );
		assertEquals( double.class, JavaTypes.fromPersistedName( "D" ) );
	}
	
	public @Test void fromPersistedNameKnown() {
		assertEquals( Object.class, JavaTypes.fromPersistedName( "Ljava/lang/Object;" ) );
		assertEquals( List.class, JavaTypes.fromPersistedName( "Ljava/util/List;" ) );
	}
	
	public @Test void fromPersistedNameUnknown() {
		assertEquals( "net.dougqh.Dne", JavaTypes.getRawClassName( JavaTypes.fromPersistedName( "Lnet/dougqh/Dne;" ) ) );
	}
}
