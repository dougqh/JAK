package net.dougqh.jak.api;

import static net.dougqh.jak.JavaAssembler.*;
import static org.junit.Assert.*;
import net.dougqh.jak.JavaClassWriter;

import org.junit.Test;

public final class CodeGenBranchTest {
	public final @Test void forwardBranchIntZero1() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "ForwardBranch" ).implements_( IntFunction.class ) );
		
		classWriter.define( public_().init() ).
			this_().
			invokespecial( Object.class, init() ).
			return_();
		
		classWriter.define(
			public_().final_().method( int.class, "calculate" ).args( int.class ) ).
			iload( 1 ).
			ifle( "not-pos" ).
			iconst_1().
			ireturn().
			
			label( "not-pos" ).
			iload( 1 ).
			ifne( "not-zero" ).
			iconst_0().
			ireturn().
			
			label( "not-zero" ).
			iconst_m1().
			ireturn();
		
		IntFunction signum = classWriter.< IntFunction >newInstance();
		assertEquals( -1, signum.calculate( -1024 ) );
		assertEquals( 0, signum.calculate( 0 ) );
		assertEquals( 1, signum.calculate( 512 ) );
	}
	
	public final @Test void forwardBranchIntZero2() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "ForwardBranch" ).implements_( IntFunction.class ) );
		
		classWriter.define( public_().init() ).
			this_().
			invokespecial( Object.class, init() ).
			return_();
		
		classWriter.define( 
			public_().final_().method( int.class, "calculate" ).args( int.class ) ).
			iload( 1 ).
			ifgt( "pos" ).
			iload( 1 ).
			ifeq( "zero" ).
			
			label( "neg" ).
			iconst_m1().
			ireturn().
			
			label( "zero" ).
			iconst_0().
			ireturn().
			
			label( "pos" ).
			iconst_1().
			ireturn();
		
		IntFunction signum = classWriter.< IntFunction >newInstance();
		assertEquals( -1, signum.calculate( -1024 ) );
		assertEquals( 0, signum.calculate( 0 ) );
		assertEquals( 1, signum.calculate( 512 ) );
	}
	
	public final @Test void loopWithContinue() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "LoopWithContinue" ).implements_( IntFunction.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( int.class, "calculate" ).
				arg( int.class, "count" ) ).
			//sum = 0
			iconst_0().
			istore( "sum" ).
			//i = 0
			iconst_0().
			istore( "i" ).
			goto_( "test" ).
			
			label( "loop" ).
			//if ( i % 10 == 0 ) continue;
			iload( "i" ).
			iconst( 10 ).
			irem().
			ifeq( "inc" ).
			//sum += i
			iload( "sum" ).
			iload( "i" ).
			iadd().
			istore( "sum" ).
			
			label( "inc" ).
			//++i
			iinc( "i" ).
			
			label( "test" ).
			iload( "i" ).
			iload( "count" ).
			if_icmple( "loop" ).
			//return sum
			iload( "sum" ).
			ireturn();
		
		IntFunction function = classWriter.< IntFunction >newInstance();
		assertEquals( 180, function.calculate( 20 ) );
	}
	
	public final @Test void reverseBranch() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "ReverseBranch" ).implements_( IntFunction.class ) );
		
		classWriter.define( public_().init() ).
			this_().
			invokespecial( Object.class, init() ).
			return_();
		
		classWriter.define( public_().final_().method( int.class, "calculate", int.class ) ).
			iconst_0().
			istore_2().
			iconst_0().
			istore_3().
			goto_( "test" ).
			
			label( "top" ).
			iload_2().
			iload_3().
			iadd().
			istore_2().
			iinc( 3, 1 ).

			label( "test" ).
			iload_3().
			iload_1().
			if_icmplt( "top" ).
			iload_2().
			ireturn();
		
		IntFunction sum = classWriter.< IntFunction >newInstance();
		
		assertEquals(
			4950,
			sum.calculate( 100 ) );
	}
	
	public final @Test void intLessCompare() {
		JavaClassWriter classWriter = define(
				public_().final_().class_( "IntLess" ).implements_( IntCompare.class ) );
			
			classWriter.defineDefaultConstructor();
			
			classWriter.define(
				public_().final_().method( boolean.class, "compare" ).
					arg( int.class, "lhs" ).
					arg( int.class, "rhs" ) ).
				iload( "lhs" ).
				iload( "rhs" ).
				if_icmplt( "less-than" ).
				iconst( false ).
				ireturn().
				label( "less-than" ).
				iconst( true ).
				ireturn();
			
			IntCompare compare = classWriter.< IntCompare >newInstance();
			assertTrue( compare.compare( 1, 2 ) );
			assertFalse( compare.compare( 1, 1 ) );
			assertFalse( compare.compare( 2, 1 ) );
	}
	
	public final @Test void intGreaterCompare() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "IntGreater" ).implements_( IntCompare.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( boolean.class, "compare" ).
				arg( int.class, "lhs" ).
				arg( int.class, "rhs" ) ).
			iload( "lhs" ).
			iload( "rhs" ).
			if_icmpgt( "greater-than" ).
			iconst( false ).
			ireturn().
			label( "greater-than" ).
			iconst( true ).
			ireturn();
		
		IntCompare compare = classWriter.< IntCompare >newInstance();
		assertFalse( compare.compare( 1, 2 ) );
		assertFalse( compare.compare( 1, 1 ) );
		assertTrue( compare.compare( 2, 1 ) );
	}
	
	public final @Test void intLessEqualCompare() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "IntLessEquals" ).implements_( IntCompare.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( boolean.class, "compare" ).
				arg( int.class, "lhs" ).
				arg( int.class, "rhs" ) ).
			iload( "lhs" ).
			iload( "rhs" ).
			if_icmple( "less-than-equals" ).
			iconst( false ).
			ireturn().
			label( "less-than-equals" ).
			iconst( true ).
			ireturn();
		
		IntCompare compare = classWriter.< IntCompare >newInstance();
		assertTrue( compare.compare( 1, 2 ) );
		assertTrue( compare.compare( 1, 1 ) );
		assertFalse( compare.compare( 2, 1 ) );
	}
	
	public final @Test void intGreaterEqualCompare() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "IntGreaterEquals" ).implements_( IntCompare.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( boolean.class, "compare" ).
				arg( int.class, "lhs" ).
				arg( int.class, "rhs" ) ).
			iload( "lhs" ).
			iload( "rhs" ).
			if_icmpge( "greater-than-equals" ).
			iconst( false ).
			ireturn().
			label( "greater-than-equals" ).
			iconst( true ).
			ireturn();
		
		IntCompare compare = classWriter.< IntCompare >newInstance();
		assertFalse( compare.compare( 1, 2 ) );
		assertTrue( compare.compare( 1, 1 ) );
		assertTrue( compare.compare( 2, 1 ) );
	}
	
	public final @Test void intEqualsCompare() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "IntEquals" ).implements_( IntCompare.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( boolean.class, "compare" ).
				arg( int.class, "lhs" ).
				arg( int.class, "rhs" ) ).
			iload( "lhs" ).
			iload( "rhs" ).
			if_icmpeq( "equals" ).
			iconst( false ).
			ireturn().
			label( "equals" ).
			iconst( true ).
			ireturn();
		
		IntCompare compare = classWriter.< IntCompare >newInstance();
		assertTrue( compare.compare( 1, 1 ) );
		assertFalse( compare.compare( 1, 2 ) );
	}
	
	public final @Test void intNotEqualsCompare() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "IntNotEquals" ).implements_( IntCompare.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( boolean.class, "compare" ).
				arg( int.class, "lhs" ).
				arg( int.class, "rhs" ) ).
			iload( "lhs" ).
			iload( "rhs" ).
			if_icmpne( "not-equals" ).
			iconst( false ).
			ireturn().
			label( "not-equals" ).
			iconst( true ).
			ireturn();
		
		IntCompare compare = classWriter.< IntCompare >newInstance();
		assertFalse( compare.compare( 1, 1 ) );
		assertTrue( compare.compare( 1, 2 ) );
	}
	
	public final @Test void ifNull() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "IfNull" ).implements_( NullChecker.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().final_().method( boolean.class, "isNull", Object.class ) ).
			aload_1().
			ifnull( "null" ).
			iconst_0().
			ireturn().
			label( "null" ).
			iconst_1().
			ireturn();
		
		NullChecker nullChecker = classWriter.< NullChecker >newInstance();
		assertTrue( nullChecker.isNull( null ) );
		assertFalse( nullChecker.isNull( "NotNull" ) );
	}
	
	public final @Test void ifNonNull() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "IfNonNull" ).implements_( NullChecker.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().final_().method( boolean.class, "isNull", Object.class ) ).
			aload_1().
			ifnonnull( "nonnull" ).
			iconst_1().
			ireturn().
			label( "nonnull" ).
			iconst_0().
			ireturn();
		
		NullChecker nullChecker = classWriter.< NullChecker >newInstance();
		assertTrue( nullChecker.isNull( null ) );
		assertFalse( nullChecker.isNull( "NotNull" ) );
	}
	
	public final @Test void ifObjectEquals() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "ObjectCompareImpl" ).implements_( ObjectCompare.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( boolean.class, "equals" ).
				arg( Object.class, "lhs" ).
				arg( Object.class, "rhs" ) ).
			aload( "lhs" ).
			aload( "rhs" ).
			if_acmpeq( "equals" ).
			iconst( false ).
			ireturn().
			label( "equals" ).
			iconst( true ).
			ireturn();
		
		ObjectCompare objectCompare = classWriter.< ObjectCompare >newInstance();
		
		assertTrue( objectCompare.equals( "Hello", "Hello" ) );
		assertFalse( objectCompare.equals( new Object(), new Object() ) );
		Object testObj = new Object();
		assertTrue( objectCompare.equals( testObj, testObj ) );
	}

	public final @Test void ifObjectNotEquals() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "ObjectCompareImpl" ).implements_( ObjectCompare.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( boolean.class, "equals" ).
				arg( Object.class, "lhs" ).
				arg( Object.class, "rhs" ) ).
			aload( "lhs" ).
			aload( "rhs" ).
			if_acmpne( "notequal" ).
			iconst( true ).
			ireturn().
			label( "notequal" ).
			iconst( false ).
			ireturn();
		
		ObjectCompare objectCompare = classWriter.< ObjectCompare >newInstance();
		
		assertTrue( objectCompare.equals( "Hello", "Hello" ) );
		assertFalse( objectCompare.equals( new Object(), new Object() ) );
		Object testObj = new Object();
		assertTrue( objectCompare.equals( testObj, testObj ) );
	}
	
	public final @Test void longCompare() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "LongCompareImpl" ).implements_( LongCompare.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( int.class, "compare" ).
				arg( long.class, "lhs" ).
				arg( long.class, "rhs" ) ).
			lload( "lhs" ).
			lload( "rhs" ).
			lcmp().
			ireturn();
		
		LongCompare longCompare = classWriter.< LongCompare >newInstance();
		
		assertEquals( -1, longCompare.compare( 0L, 1L ) );
		assertEquals( 0, longCompare.compare( 1L, 1L ) );
		assertEquals( 1, longCompare.compare( 1L, 0L ) );
	}
	
	public final @Test void floatLessCompare() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "FloatCompareImpl" ).implements_( FloatCompare.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( int.class, "compare" ).
				arg( float.class, "lhs" ).
				arg( float.class, "rhs" ) ).
			fload( "lhs" ).
			fload( "rhs" ).
			fcmpl().
			ireturn();
		
		FloatCompare floatCompare = classWriter.< FloatCompare >newInstance();
		
		assertEquals( -1, floatCompare.compare( 0F, 1F ) );
		assertEquals( 0, floatCompare.compare( 0F, 0F ) );
		assertEquals( 1, floatCompare.compare( 1F, 0F ) );
	}
	
	public final @Test void floatGreaterCompare() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "FloatCompareImpl" ).implements_( FloatCompare.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( int.class, "compare" ).
				arg( float.class, "lhs" ).
				arg( float.class, "rhs" ) ).
			fload( "lhs" ).
			fload( "rhs" ).
			fcmpg().
			ireturn();
		
		FloatCompare floatCompare = classWriter.< FloatCompare >newInstance();
		
		assertEquals( -1, floatCompare.compare( 0F, 1F ) );
		assertEquals( 0, floatCompare.compare( 0F, 0F ) );
		assertEquals( 1, floatCompare.compare( 1F, 0F ) );
	}
	
	public final @Test void doubleLessCompare() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "DoubleCompareImpl" ).implements_( DoubleCompare.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( int.class, "compare" ).
				arg( double.class, "lhs" ).
				arg( double.class, "rhs" ) ).
			dload( "lhs" ).
			dload( "rhs" ).
			dcmpl().
			ireturn();
		
		DoubleCompare doubleCompare = classWriter.< DoubleCompare >newInstance();
		
		assertEquals( -1, doubleCompare.compare( 0D, 1D ) );
		assertEquals( 0, doubleCompare.compare( 0D, 0D ) );
		assertEquals( 1, doubleCompare.compare( 1D, 0D ) );
	}
	
	public final @Test void doubleGreaterCompare() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "DoubleCompareImpl" ).implements_( DoubleCompare.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( int.class, "compare" ).
				arg( double.class, "lhs" ).
				arg( double.class, "rhs" ) ).
			dload( "lhs" ).
			dload( "rhs" ).
			dcmpg().
			ireturn();
		
		DoubleCompare doubleCompare = classWriter.< DoubleCompare >newInstance();
		
		assertEquals( -1, doubleCompare.compare( 0D, 1D ) );
		assertEquals( 0, doubleCompare.compare( 0D, 0D ) );
		assertEquals( 1, doubleCompare.compare( 1D, 0D ) );
	}
	
	public static interface IntFunction {
		public abstract int calculate( final int x );
	}
	
	public static interface NullChecker {
		public abstract boolean isNull( final Object object );
	}
	
	public static interface ObjectCompare {
		public abstract boolean equals(
			final Object lhs,
			final Object rhs );
	}
	
	public static interface IntCompare {
		public abstract boolean compare(
			final int lhs,
			final int rhs );
	}
	
	public static interface LongCompare {
		public abstract int compare(
			final long lhs,
			final long rhs );
	}
	
	public static interface FloatCompare {
		public abstract int compare(
			final float lhs,
			final float rhs );
	}
	
	public static interface DoubleCompare {
		public abstract int compare(
			final double lhs,
			final double rhs );
	}
}
