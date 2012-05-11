package net.dougqh.jak.assembler;

import static org.junit.Assert.*;

import java.lang.reflect.Type;

import net.dougqh.jak.JakContext;
import net.dougqh.java.meta.types.primitives.boolean_;

import org.junit.Test;

public final class JakExpressionsTest {
	@Test
	public final void arbitraryPrimitive() {
		assertEquals(
			boolean.class,
			new expr< boolean_ >() {}.type( new FakeContext() ) );
	}
	
	@Test
	public final void arbitaryReferenceType() {
		assertEquals(
			String.class,
			new expr< String >() {}.type( new FakeContext() ) );
	}
	
	private static abstract class expr<T> extends JakArbitraryExpression<T> {}
	
	private static final class FakeContext implements JakContext {
		@Override
		public final Type localType( final String name ) {
			throw new UnsupportedOperationException();
		}
	}
}
