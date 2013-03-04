package net.dougqh.jak.jvm.assembler.macros.api;

import java.lang.reflect.Type;

import net.dougqh.jak.jvm.JvmContext;
import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;
import net.dougqh.jak.jvm.assembler.macros.expr;
import net.dougqh.java.meta.types.primitives.boolean_;
import net.dougqh.java.meta.types.primitives.byte_;
import net.dougqh.java.meta.types.primitives.char_;
import net.dougqh.java.meta.types.primitives.double_;
import net.dougqh.java.meta.types.primitives.float_;
import net.dougqh.java.meta.types.primitives.int_;
import net.dougqh.java.meta.types.primitives.long_;
import net.dougqh.java.meta.types.primitives.short_;

import org.junit.Test;

import static net.dougqh.jak.Jak.*;

import static net.dougqh.jak.assembler.JakAsm.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

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
	public final void booleanArbitraryExpr() {
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
	public final void booleanConstExpr() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "True" ).extends_( Const.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( boolean.class, "getBoolean" ) ).
			ireturn( true_() );
		
		Const trueConst = classWriter.< Const >newInstance();
		assertThat( trueConst.getBoolean(), is( true ) );		
	}
	
	@Test
	public final void byteArbitraryExpr() {
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
	public final void byteConstExpr() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "Two" ).extends_( Const.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( byte.class, "getByte" ) ).
			ireturn( const_( (byte)2 ) );
		
		Const twoConst = classWriter.< Const >newInstance();
		assertThat( twoConst.getByte(), is( (byte)2 ) );
	}
	
	@Test
	public final void charArbitraryExpr() {
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
	public final void charConstExpr() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "B" ).extends_( Const.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( char.class, "getChar" ) ).
			ireturn( const_( 'b' ) );
		
		Const aConst = classWriter.< Const >newInstance();
		assertThat( aConst.getChar(), is( 'b' ) );		
	}
	
	@Test
	public final void shortArbitraryExpr() {
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
	public final void shortConstExpr() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "Max" ).extends_( Const.class ) );
			
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( short.class, "getShort" ) ).
			ireturn( const_( Short.MAX_VALUE ) );
		
		Const maxConst = classWriter.< Const >newInstance();
		assertThat( maxConst.getShort(), is( Short.MAX_VALUE ) );
	}
	
	@Test
	public final void intArbitraryExpr() {
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
	public final void intConstExpr() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "Min" ).extends_( Const.class ) );
			
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( int.class, "getInt" ) ).
			ireturn( const_( Integer.MIN_VALUE ) );
		
		Const minConst = classWriter.< Const >newInstance();
		assertThat( minConst.getInt(), is( Integer.MIN_VALUE ) );	
	}
	
	@Test
	public final void longArbitraryExpr() {
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
	public final void longConstExpr() {
		JvmClassWriter classWriter = new JvmWriter().define(
				public_().class_( "Zero" ).extends_( Const.class ) );
				
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( long.class, "getLong" ) ).
			lreturn( const_( 0L ) );
		
		Const zeroConst = classWriter.< Const >newInstance();
		assertThat( zeroConst.getLong(), is( 0L ) );
	}
	
	@Test
	public final void floatArbitraryExpr() {
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
	public final void floatConstExpr() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "One" ).extends_( Const.class ) );
			
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( float.class, "getFloat" ) ).
			freturn( const_( 1F ) );
		
		Const oneConst = classWriter.< Const >newInstance();
		assertThat( oneConst.getFloat(), is( 1F ) );
	}
	
	@Test
	public final void doubleArbitraryExpr() {
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
	public final void doubleConstExpr() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "Two" ).extends_( Const.class ) );
			
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( double.class, "getDouble" ) ).
			dreturn( const_( 2D ) );
		
		Const twoConst = classWriter.< Const >newInstance();
		assertThat( twoConst.getDouble(), is( 2D ) );
	}
	
	@Test
	public final void objectArbitraryExpr() {
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
	
	@Test
	public final void stringConstExpr() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "Hello" ).extends_( Const.class ) );
			
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( Object.class, "getObject" ) ).
			areturn( const_( "Hello World" ) );
		
		Const helloConst = classWriter.< Const >newInstance();
		assertThat( helloConst.getObject(), is( (Object)"Hello World" ) );		
	}
	
	@Test
	public final void classConstExpr() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "Foo" ).extends_( Const.class ) );
			
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( Object.class, "getObject" ) ).
			areturn( const_( Const.class ) );
		
		Const helloConst = classWriter.< Const >newInstance();
		assertThat( helloConst.getObject(), is( (Object)Const.class ) );
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
	
	private static final class FakeContext implements JvmContext {
		@Override
		public final Type localType( final String name, final Type expectedType ) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Type thisType() {
			throw new UnsupportedOperationException();
		}
	}
}
