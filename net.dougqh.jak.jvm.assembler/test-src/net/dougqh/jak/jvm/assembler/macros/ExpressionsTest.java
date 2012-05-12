package net.dougqh.jak.jvm.assembler.macros;

import static net.dougqh.jak.Jak.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Type;

import net.dougqh.jak.JakContext;
import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;
import net.dougqh.java.meta.types.primitives.boolean_;
import net.dougqh.java.meta.types.primitives.byte_;
import net.dougqh.java.meta.types.primitives.char_;
import net.dougqh.java.meta.types.primitives.double_;
import net.dougqh.java.meta.types.primitives.float_;
import net.dougqh.java.meta.types.primitives.int_;
import net.dougqh.java.meta.types.primitives.long_;
import net.dougqh.java.meta.types.primitives.short_;

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
			public_().class_( "True" ).extends_( Const.class ) );
		
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
	
	@Test
	public final void byteExpr() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "Two" ).extends_( Const.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( byte.class, "getByte" ) ).
			ireturn( new expr< byte_ >() {
				@Override
				protected void eval() {
					iconst_2();
				}
			} );
		
		Const twoConst = classWriter.< Const >newInstance();
		assertThat( twoConst.getByte(), is( (byte)2 ) );		
	}
	
	@Test
	public final void charExpr() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "A" ).extends_( Const.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( char.class, "getChar" ) ).
			ireturn( new expr< char_ >() {
				@Override
				protected void eval() {
					iconst( 'a' );
				}
			} );
		
		Const aConst = classWriter.< Const >newInstance();
		assertThat( aConst.getChar(), is( 'a' ) );		
	}
	
	@Test
	public final void shortExpr() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "Max" ).extends_( Const.class ) );
			
			classWriter.defineDefaultConstructor();
			
			classWriter.define( public_().method( short.class, "getShort" ) ).
				ireturn( new expr< short_ >() {
					@Override
					protected void eval() {
						iconst( Short.MAX_VALUE );
					}
				} );
			
			Const maxConst = classWriter.< Const >newInstance();
			assertThat( maxConst.getShort(), is( Short.MAX_VALUE ) );
	}
	
	@Test
	public final void intExpr() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "Min" ).extends_( Const.class ) );
			
			classWriter.defineDefaultConstructor();
			
			classWriter.define( public_().method( int.class, "getInt" ) ).
				ireturn( new expr< int_ >() {
					@Override
					protected void eval() {
						iconst( Integer.MIN_VALUE );
					}
				} );
			
			Const minConst = classWriter.< Const >newInstance();
			assertThat( minConst.getInt(), is( Integer.MIN_VALUE ) );		
	}
	
	@Test
	public final void longExpr() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "Zero" ).extends_( Const.class ) );
			
			classWriter.defineDefaultConstructor();
			
			classWriter.define( public_().method( long.class, "getLong" ) ).
				lreturn( new expr< long_ >() {
					@Override
					protected void eval() {
						lconst( 0L );
					}
				} );
			
			Const zeroConst = classWriter.< Const >newInstance();
			assertThat( zeroConst.getLong(), is( 0L ) );
	}
	
	@Test
	public final void floatExpr() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "One" ).extends_( Const.class ) );
			
			classWriter.defineDefaultConstructor();
			
			classWriter.define( public_().method( float.class, "getFloat" ) ).
				freturn( new expr< float_ >() {
					@Override
					protected void eval() {
						fconst( 1F );
					}
				} );
			
			Const oneConst = classWriter.< Const >newInstance();
			assertThat( oneConst.getFloat(), is( 1F ) );
	}
	
	@Test
	public final void doubleExpr() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "Two" ).extends_( Const.class ) );
			
			classWriter.defineDefaultConstructor();
			
			classWriter.define( public_().method( double.class, "getDouble" ) ).
				dreturn( new expr< double_ >() {
					@Override
					protected void eval() {
						dconst( 2D );
					}
				} );
			
			Const twoConst = classWriter.< Const >newInstance();
			assertThat( twoConst.getDouble(), is( 2D ) );
	}
	
	@Test
	public final void objectExpr() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "Hello" ).extends_( Const.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( Object.class, "getObject" ) ).
			areturn( new expr< Object >() {
				@Override
				protected void eval() {
					ldc( "Hello World" );
				}
			} );
		
		Const helloConst = classWriter.< Const >newInstance();
		assertThat( helloConst.getObject(), is( (Object)"Hello World" ) );
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
