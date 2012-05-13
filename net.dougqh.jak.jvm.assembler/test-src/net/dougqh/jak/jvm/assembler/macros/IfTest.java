package net.dougqh.jak.jvm.assembler.macros;

import static net.dougqh.jak.Jak.*;
import static net.dougqh.jak.assembler.JakAsm.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;

import org.junit.Test;

public final class IfTest {
	@Test
	public final void if_elseif_else_terminal() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "Signum" ).extends_( IntConditional.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().final_().method( int_, "exec" ).arg( int_, "num" ) ).
			if_( gt( "num", 0 ), new stmt() {
				@Override
				protected final void body() {
					ireturn( 1 );
				}				
			} ).
			elseif( eq( "num", 0 ), new stmt() {
				@Override
				protected final void body() {
					ireturn( 0 );
				}
			} ).
			else_( new stmt() {
				@Override
				protected final void body() {
					ireturn( -1 );
				}
			} );
		
		IntConditional signum = classWriter.newInstance();
		assertThat( signum.exec( 10 ), is( 1 ) );
		assertThat( signum.exec( 0 ), is( 0 ) );
		assertThat( signum.exec( -10 ), is( -1 ) );		
	}
	
	@Test
	public final void if_() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "Abs" ).extends_( IntConditional.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().final_().method( int_, "exec" ).arg( int_, "num" ) ).
			if_( ge( "num", 0 ), new stmt() {
				@Override
				protected final void body() {
					ireturn( "num" );
				}
			} ).
			ineg( "num" ).
			ireturn();
		
		IntConditional abs = classWriter.newInstance();
		assertThat( abs.exec( 10 ), is( 10 ) );
		assertThat( abs.exec( 0 ), is( 0 ) );
		assertThat( abs.exec( -10 ), is( 10 ) );
	}
	
	@Test
	public final void if_non_terminal() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "Abs" ).extends_( IntConditional.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( int_, "exec" ).arg( int_, "num" ) ).
			if_( ge( "num", 0 ), new stmt() {
				@Override
				protected final void body() {
					ireturn( "num" );
				}
			} ).else_( new stmt() {
				@Override
				protected final void body() {
					ineg( "num" ).
					ireturn();
				}				
			} );
		
		IntConditional abs = classWriter.newInstance();
		assertThat( abs.exec( 10 ), is( 10 ) );
		assertThat( abs.exec( 0 ), is( 0 ) );
		assertThat( abs.exec( -10 ), is( 10 ) );
	}
	
	@Test
	public final void if_equal_zero() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "Abs" ).extends_( IntConditional.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( int_, "exec" ).arg( int_, "num" ) ).
			if_( ge( "num", 0 ), new stmt() {
				@Override
				protected final void body() {
					ireturn( "num" );
				}
			} ).else_( new stmt() {
				@Override
				protected final void body() {
					ineg( "num" ).
					ireturn();
				}				
			} );
		
		IntConditional abs = classWriter.newInstance();
		assertThat( abs.exec( 10 ), is( 10 ) );
		assertThat( abs.exec( 0 ), is( 0 ) );
		assertThat( abs.exec( -10 ), is( 10 ) );
	}
	
	@Test
	public final void if_equal_to_zero() {
		JvmClassWriter classWriter = new JvmWriter().define(
				public_().final_().class_( "IsZero" ).extends_( Conditional.class ) );
			
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( boolean_, "predicate", int_, "value" ) ).
			ireturn( eq( "value", 0 ) );
		
		Conditional isZero = classWriter.< Conditional >newInstance();
		assertThat( isZero.predicate( 0 ), is( true ) );
		assertThat( isZero.predicate( -1 ), is( false ) );
	}

	@Test
	public final void if_not_equal_to_zero() {
		JvmClassWriter classWriter = new JvmWriter().define(
				public_().final_().class_( "IsNotZero" ).extends_( Conditional.class ) );
			
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( boolean_, "predicate", int_, "value" ) ).
			ireturn( ne( "value", 0 ) );
		
		Conditional isNotZero = classWriter.< Conditional >newInstance();
		assertThat( isNotZero.predicate( 0 ), is( false ) );
		assertThat( isNotZero.predicate( -1 ), is( true ) );
	}
	
	@Test
	public final void if_less_than_zero() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "IsLessThanZero" ).extends_( Conditional.class ) );
			
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( boolean_, "predicate", int_, "value" ) ).
			ireturn( lt( "value", 0 ) );
		
		Conditional isLessThanZero = classWriter.< Conditional >newInstance();
		assertThat( isLessThanZero.predicate( 0 ), is( false ) );
		assertThat( isLessThanZero.predicate( -1 ), is( true ) );
	}
	
	@Test
	public final void if_less_than_or_equal_to_zero() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "IsLessThanOrEqualToZero" ).extends_( Conditional.class ) );
			
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( boolean_, "predicate", int_, "value" ) ).
			ireturn( le( "value", 0 ) );
		
		Conditional isLessThanOrEqualToZero = classWriter.< Conditional >newInstance();
		assertThat( isLessThanOrEqualToZero.predicate( 1 ), is( false ) );
		assertThat( isLessThanOrEqualToZero.predicate( 0 ), is( true ) );
		assertThat( isLessThanOrEqualToZero.predicate( -1 ), is( true ) );
	}
	
	@Test
	public final void if_greater_than_zero() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "IsGreaterThanZero" ).extends_( Conditional.class ) );
			
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( boolean_, "predicate", int_, "value" ) ).
			ireturn( gt( "value", 0 ) );
		
		Conditional isLessThanZero = classWriter.< Conditional >newInstance();
		assertThat( isLessThanZero.predicate( 0 ), is( false ) );
		assertThat( isLessThanZero.predicate( 1 ), is( true ) );
	}
	
	@Test
	public final void if_greater_than_or_equal_to_zero() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "IsGreaterThanOrEqualToZero" ).extends_( Conditional.class ) );
			
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( boolean_, "predicate", int_, "value" ) ).
			ireturn( ge( "value", 0 ) );
		
		Conditional isGreaterThanOrEqualToZero = classWriter.< Conditional >newInstance();
		assertThat( isGreaterThanOrEqualToZero.predicate( 1 ), is( true ) );
		assertThat( isGreaterThanOrEqualToZero.predicate( 0 ), is( true ) );
		assertThat( isGreaterThanOrEqualToZero.predicate( -1 ), is( false ) );
	}
	
	@Test
	public final void if_less_than() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "LessThan" ).extends_( Conditional.class ) );
			
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( boolean_, "predicate", int_, "lhs", int_, "rhs" ) ).
			ireturn( lt( "lhs", "rhs" ) );
		
		Conditional lessThan = classWriter.< Conditional >newInstance();
		assertThat( lessThan.predicate( 0, 1 ), is( true ) );
		assertThat( lessThan.predicate( 1, 1 ), is( false ) );
		assertThat( lessThan.predicate( 2, 1 ), is( false ) );
	}
	
	@Test
	public final void if_less_than_or_equal() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "LessThanOrEqual" ).extends_( Conditional.class ) );
			
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( boolean_, "predicate", int_, "lhs", int_, "rhs" ) ).
			ireturn( le( "lhs", "rhs" ) );
		
		Conditional lessThanOrEqual = classWriter.< Conditional >newInstance();
		assertThat( lessThanOrEqual.predicate( 0, 1 ), is( true ) );
		assertThat( lessThanOrEqual.predicate( 1, 1 ), is( true ) );
		assertThat( lessThanOrEqual.predicate( 2, 1 ), is( false ) );	
	}
	
	@Test
	public final void if_greater_than() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "GreaterThan" ).extends_( Conditional.class ) );
			
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( boolean_, "predicate", int_, "lhs", int_, "rhs" ) ).
			ireturn( gt( "lhs", "rhs" ) );
		
		Conditional greaterThan = classWriter.< Conditional >newInstance();
		assertThat( greaterThan.predicate( 0, 1 ), is( false ) );
		assertThat( greaterThan.predicate( 1, 1 ), is( false ) );
		assertThat( greaterThan.predicate( 2, 1 ), is( true ) );
	}
	
	@Test
	public final void if_greater_than_or_equal() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "GreaterThanOrEqual" ).extends_( Conditional.class ) );
			
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( boolean_, "predicate", int_, "lhs", int_, "rhs" ) ).
			ireturn( ge( "lhs", "rhs" ) );
		
		Conditional greaterThanOrEqual = classWriter.< Conditional >newInstance();
		assertThat( greaterThanOrEqual.predicate( 0, 1 ), is( false ) );
		assertThat( greaterThanOrEqual.predicate( 1, 1 ), is( true ) );
		assertThat( greaterThanOrEqual.predicate( 2, 1 ), is( true ) );
	}
	
	@Test
	public final void if_equal() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "Equal" ).extends_( Conditional.class ) );
			
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( boolean_, "predicate", int_, "lhs", int_, "rhs" ) ).
			ireturn( eq( "lhs", "rhs" ) );
		
		Conditional equal = classWriter.< Conditional >newInstance();
		assertThat( equal.predicate( 1, 1 ), is( true ) );
		assertThat( equal.predicate( 1, 2 ), is( false ) );
	}
	
	@Test
	public final void if_not_equal() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "NotEqual" ).extends_( Conditional.class ) );
			
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( boolean_, "predicate", int_, "lhs", int_, "rhs" ) ).
			ireturn( ne( "lhs", "rhs" ) );
		
		Conditional notEqual = classWriter.< Conditional >newInstance();
		assertThat( notEqual.predicate( 1, 1 ), is( false ) );
		assertThat( notEqual.predicate( 1, 2 ), is( true ) );
	}
	
	@Test
	public final void if_null() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "Null" ).extends_( Conditional.class ) );
			
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( boolean_, "predicate", Object.class, "value" ) ).
			ireturn( eq( "value", null_() ) );
		
		Conditional notEqual = classWriter.< Conditional >newInstance();
		assertThat( notEqual.predicate( null ), is( true ) );
		assertThat( notEqual.predicate( "Foo" ), is( false ) );
	}
	
	@Test
	public final void if_nonnull() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "NonNull" ).extends_( Conditional.class ) );
			
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( boolean_, "predicate", Object.class, "value" ) ).
			ireturn( ne( "value", null_() ) );
		
		Conditional notEqual = classWriter.< Conditional >newInstance();
		assertThat( notEqual.predicate( null ), is( false ) );
		assertThat( notEqual.predicate( "Foo" ), is( true ) );
	}
	
	@Test
	public final void if_ref_equals() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "RefEquals" ).extends_( Conditional.class ) );
			
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( boolean_, "predicate", Object.class, "lhs", Object.class, "rhs" ) ).
			ireturn( eq( "lhs", "rhs" ) );
		
		Conditional notEqual = classWriter.< Conditional >newInstance();
		assertThat( notEqual.predicate( null, null ), is( true ) );
		assertThat( notEqual.predicate( "Foo", "Bar" ), is( false ) );
	}
	
	public static abstract class IntConditional {
		public int exec( final int num ) {
			throw new UnsupportedOperationException();
		}
	}
	
	public static abstract class Conditional {
		public boolean predicate( final int value ) {
			throw new UnsupportedOperationException();
		}
		
		public boolean predicate( final int lhs, final int rhs ) {
			throw new UnsupportedOperationException();
		}
		
		public boolean predicate( final Object value ) {
			throw new UnsupportedOperationException();
		}
		
		public boolean predicate( final Object lhs, final Object rhs ) {
			throw new UnsupportedOperationException();
		}
	}
}
