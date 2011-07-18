package net.dougqh.java.meta.types.api;

import static org.junit.Assert.*;
import net.dougqh.java.meta.types.JavaTypes;

import org.junit.Test;

public final class JavaTypesArrayTest {
	public final @Test void arrayDepth() {
		assertEquals( 0, JavaTypes.getArrayDepth( int.class ) );
		assertEquals( 1, JavaTypes.getArrayDepth( int[].class ) );
		assertEquals( 2, JavaTypes.getArrayDepth( boolean[][].class ) );
		assertEquals( 0, JavaTypes.getArrayDepth( Object.class ) );
		assertEquals( 1, JavaTypes.getArrayDepth( Object[].class ) );
		assertEquals( 2, JavaTypes.getArrayDepth( Object[][].class ) );
		assertEquals( 6, JavaTypes.getArrayDepth( Object[][][][][][].class ) );
	}
	
	public final @Test void arrayType() {
		assertEquals( int[].class, JavaTypes.array( int.class ) );
		assertEquals( boolean[].class, JavaTypes.array( boolean.class, 1 ) );
		assertEquals( short[][].class, JavaTypes.array( short.class, 2 ) );
		assertEquals( String[].class, JavaTypes.array( String.class ) );
		assertEquals( Integer[].class, JavaTypes.array( Integer.class, 1 ) );
		assertEquals( Number[][][].class, JavaTypes.array( Number.class, 3 ) );
	}
}
