package net.dougqh.jak.jvm.assembler.macros;

import static net.dougqh.jak.Jak.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Type;

import net.dougqh.jak.JakContext;
import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;
import net.dougqh.java.meta.types.primitives.boolean_;

import org.junit.Test;

public final class ExpressionsTest {
	@Test
	public final void arbitraryPrimitiveTypeCheck() {
		expr< boolean_ > expr = new expr< boolean_ >() {
			@Override
			protected void eval() {
				true_();
			}
		};
		
		assertEquals( boolean.class, expr.type( new FakeContext() ) );
	}
	
	@Test
	public final void arbitaryReferenceTypeCheck() {
		expr< String > expr = new expr< String >() {
			@Override
			protected final void eval() {
				ldc("Hello World");
			}
		};
		
		assertEquals( String.class, expr.type( new FakeContext() ) );
	}
	
	@Test
	public final void booleanExpr() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "Zero" ).extends_( Const.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( boolean.class, "getBoolean" ) ).
			ireturn( new expr< boolean_ >() {
				@Override
				protected void eval() {
					true_();
				}
			} );
		
		Const trueConst = classWriter.< Const >newInstance();
		assertThat( trueConst.getBoolean(), is( true ) );
	}
	
	public static abstract class Const {
		protected boolean getBoolean() {
			throw new UnsupportedOperationException();
		}
		
		protected byte getByte() {
			throw new UnsupportedOperationException();
		}
		
		protected char getChar() {
			throw new UnsupportedOperationException();
		}
		
		protected short getShort() {
			throw new UnsupportedOperationException();
		}
		
		protected int getInt() {
			throw new UnsupportedOperationException();
		}
		
		protected long getLong() {
			throw new UnsupportedOperationException();
		}
		
		protected float getFloat() {
			throw new UnsupportedOperationException();
		}
		
		protected double getDouble() {
			throw new UnsupportedOperationException();
		}
		
		protected Object getObject() {
			throw new UnsupportedOperationException();
		}
	}
	
	private static final class FakeContext implements JakContext {
		@Override
		public final Type localType( final String name ) {
			throw new UnsupportedOperationException();
		}
	}
}
