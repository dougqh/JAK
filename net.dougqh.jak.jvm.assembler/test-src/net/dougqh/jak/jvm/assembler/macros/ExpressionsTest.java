package net.dougqh.jak.jvm.assembler.macros;

import static org.junit.Assert.*;

import java.lang.reflect.Type;

import net.dougqh.jak.JakContext;
import net.dougqh.java.meta.types.primitives.boolean_;

import org.junit.Test;

public final class ExpressionsTest {
	@Test
	public final void arbitraryPrimitive() {
		expr< boolean_ > expr = new expr< boolean_ >() {
			@Override
			protected void eval() {
				true_();
			}
		};
		
		assertEquals( boolean.class, expr.type( new FakeContext() ) );
	}
	
	@Test
	public final void arbitaryReferenceType() {
		expr< String > expr = new expr< String >() {
			@Override
			protected final void eval() {
				ldc("Hello World");
			}
		};
		
		assertEquals( String.class, expr.type( new FakeContext() ) );
	}
	
	private static final class FakeContext implements JakContext {
		@Override
		public final Type localType( final String name ) {
			throw new UnsupportedOperationException();
		}
	}
}
