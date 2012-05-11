package net.dougqh.java.meta.types.api;

import static org.junit.Assert.*;
import net.dougqh.java.meta.types.JavaTypes;
import net.dougqh.java.meta.types.primitives.boolean_;
import net.dougqh.java.meta.types.primitives.byte_;
import net.dougqh.java.meta.types.primitives.char_;
import net.dougqh.java.meta.types.primitives.double_;
import net.dougqh.java.meta.types.primitives.float_;
import net.dougqh.java.meta.types.primitives.int_;
import net.dougqh.java.meta.types.primitives.long_;
import net.dougqh.java.meta.types.primitives.short_;

import org.junit.Test;

public final class JavaTypePrimitivesTest {
	@Test
	public final void boolean_() {
		assertEquals( boolean.class, JavaTypes.resolve( boolean_.class ) );
	}
	
	@Test
	public final void byte_() {
		assertEquals( byte.class, JavaTypes.resolve( byte_.class ) );
	}
	
	@Test
	public final void char_() {
		assertEquals( char.class, JavaTypes.resolve( char_.class ) );
	}
	
	@Test
	public final void short_() {
		assertEquals( short.class, JavaTypes.resolve( short_.class ) );
	}
	
	@Test
	public final void int_() {
		assertEquals( int.class, JavaTypes.resolve( int_.class ) );
	}
	
	@Test
	public final void long_() {
		assertEquals( long.class, JavaTypes.resolve( long_.class ) );
	}
	
	@Test
	public final void float_() {
		assertEquals( float.class, JavaTypes.resolve( float_.class ) );
	}
	
	@Test
	public final void double_() {
		assertEquals( double.class, JavaTypes.resolve( double_.class ) );
	}
}
