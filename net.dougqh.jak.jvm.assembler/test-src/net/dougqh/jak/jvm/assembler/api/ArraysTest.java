package net.dougqh.jak.jvm.assembler.api;

import static net.dougqh.jak.Jak.*;
import static net.dougqh.jak.assembler.JakAsm.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;
import net.dougqh.jak.jvm.assembler.api.LoadAndStoreTest.ObjectArrayManipulator;
import net.dougqh.jak.jvm.assembler.macros.For;

import org.junit.Test;

public final class ArraysTest {
	public final @Test void arrayCreation() {
		assertTrue( makePrimitiveArray( boolean.class ) instanceof boolean[] );
		assertTrue( makePrimitiveArray( byte.class ) instanceof byte[] );
		assertTrue( makePrimitiveArray( char.class ) instanceof char[] );
		assertTrue( makePrimitiveArray( short.class ) instanceof short[] );
		assertTrue( makePrimitiveArray( int.class ) instanceof int[] );
		assertTrue( makePrimitiveArray( long.class ) instanceof long[] );
		assertTrue( makePrimitiveArray( float.class ) instanceof float[] );
		assertTrue( makePrimitiveArray( double.class ) instanceof double[] );
		assertTrue( makeObjectArray( Object.class ) instanceof Object[] );
		assertTrue( makeObjectArray( String.class ) instanceof String[] );
	}
	
	private final Object makePrimitiveArray( final Class< ? > componentType ) {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "PrimitiveArrayCreator" ).implements_( Creator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( Object.class, "create" ) ).
			iconst_0().
			newarray( componentType ).
			areturn();
		
		Creator creator = classWriter.< Creator >newInstance();
		return creator.create();
	}
	
	private final Object makeObjectArray( final Class< ? > componentType ) {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "ObjectArrayCreator" ).implements_( Creator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( Object.class, "create" ) ).
			iconst_0().
			anewarray( componentType ).
			areturn();
		
		Creator creator = classWriter.< Creator >newInstance();
		return creator.create();
	}
	
	public final @Test void integerArrayStore() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "IAStore" ).implements_( Creator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( Object.class, "create" ) ).
			iconst_3().
			newarray( int.class ).
			dup().
			iconst_0().
			iconst_0().
			iastore().
			dup().
			iconst_1().
			iconst_1().
			iastore().
			dup().
			iconst_2().
			iconst_2().
			iastore().
			iarray( 0, 1, 2 ).
			areturn();
		
		Creator creator = classWriter.< Creator >newInstance();
		
		assertArrayEquals(
			new int[] { 0, 1, 2 },
			(int[])creator.create() );
	}
	
	public final @Test void integerArrayLoad() throws IOException {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "Sum" ).implements_( IntFunction.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().method( int.class, "process" ).
				arg( int[].class, "array" ) ).
			
			//sum = 0
			ideclare( "sum" ).
			istore( 0, "sum" ).
			
			//i = 0
			ideclare( "i" ).
			istore( 0, "i" ).
			goto_( "test" ).
			
			label( "loop" ).
			//sum += array[ i ]
			iload( "sum" ).
			aload( "array" ).
			iload( "i" ).
			iaload().
			iadd().
			istore( "sum" ).
			
			//i++
			iinc( "i", 1 ).
			
			label( "test" ).
			//if i < array.length then goto loop
			iload( "i" ).
			aload( "array" ).
			arraylength().
			if_icmplt( "loop" ).
			
			//return sum
			iload( "sum" ).
			ireturn();
		
		IntFunction sum = classWriter.< IntFunction >newInstance();
		
		classWriter.writeTo( new File( "/Users/dougqh/Desktop/bc-scratch" ) );
		
		assertEquals(
			59,
			sum.process( new int[] { 1, 8, 50 } ) );
	}
	
	public final @Test void longArrays() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "Rotate" ).extends_( ArrayManipulator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( void.class, "alter" ).
				arg( long[].class, "array" ) ).

				//tmp = array[ array.length - 1 ]
				ldeclare( "tmp" ).
				
				aload( "array" ).
				arraylength( "array" ).
				iconst_1().
				isub().
				laload().
				lstore( "tmp" ).

				macro( new For() {
					protected void preinit() {
					}
					
					protected void init() {
						//i = array.length - 2
						ideclare( "i" ).
						arraylength( "array" ).
						iconst_2().
						isub().
						istore( "i" );
					}
					
					protected void test( final String trueLabel ) {
						if_( ge( "i", 0 ), trueLabel );
					}
					
					protected void step() {
						idec( "i" );
					}
					
					protected void body() {
						//array[ i + 1 ] = array[ i ];
						aload( "array" ).
						iload( "i" ).
						iconst_1().
						iadd().
						laload( "array", "i" ).
						lastore();
					}
				} ).
			
				//array[ 0 ] = tmp;
				start_lastore( "array", 0 ).
				lload( "tmp" ).
				lastore().
				
				return_();
		
		ArrayManipulator rotate = classWriter.< ArrayManipulator >newInstance();
		
		long[] values = new long[] { 10L, 20L, 30L, 40L, 50L };
		rotate.alter( values );
		
		assertArrayEquals(
			new long[] { 50L, 10L, 20L, 30L, 40L },
			values );
	}
	
	public final @Test void booleanArrays() {
		JvmClassWriter classWriter = new JvmWriter().define( 
			public_().final_().class_( "BooleanFlipper" ).extends_( ArrayManipulator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( void.class, "alter" ).
				arg( boolean[].class, "array" ) ).
				macro( new For() {
					protected void init() {
						ideclare( 0, "i" );
					}
					
					protected void test( final String trueLabel ) {
						iload( "i" );
						arraylength( "array" );
						if_icmplt( trueLabel );
					}
					
					protected void body() {
						//array[ i ] = ! array[ i ];
						start_bastore( "array", "i" );
						baload( "array", "i" );
						inot();
						bastore();
					}
					
					protected void step() {
						iinc( "i" );
					}
				} ).
				return_();
		
		ArrayManipulator flipper = classWriter.< ArrayManipulator >newInstance();
		
		boolean[] values = new boolean[] { false, true, false, false, true };
		flipper.alter( values );
		
		assertTrue(
			Arrays.equals(
				new boolean[] { true, false, true, true, false },
				values ) );
	}
	
	public final @Test void charArrays() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "CharIncrementer" ).extends_( ArrayManipulator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( void.class, "alter" ).
				arg( char[].class, "array" ) ).
				macro( new For() {
					protected void init() {
						ideclare( 0, "i" );
					}
					
					protected void test( final String trueLabel ) {
						iload( "i" );
						arraylength( "array" );
						if_icmplt( trueLabel );
					}
					
					protected void body() {
						//array[ i ] = array[ i ] + 1
						start_castore( "array", "i" );
						caload( "array", "i" );
						iconst_1();
						iadd();
						castore();
					}
					
					protected void step() {
						iinc( "i" );
					}
				} ).
				return_();
		
		ArrayManipulator incrementor = classWriter.< ArrayManipulator >newInstance();
		
		char[] values = new char[] { 'h', 'e', 'l', 'l', 'o' };
		incrementor.alter( values );
		
		assertArrayEquals(
			new char[] { 'i', 'f', 'm', 'm', 'p' },
			values );
	}
	
	public final @Test void shortArray() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "ShortSquared" ).extends_( ArrayManipulator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( void.class, "alter" ).
				arg( short[].class, "array" ) ).
				macro( new For() {
					@Override
					protected void init() {
						ideclare( 0, "i" );
					}
					
					protected void test( final String trueLabel ) {
						iload( "i" );
						arraylength( "array" );
						if_icmplt( trueLabel );
					}
					
					protected void step() {
						iinc( "i" );
					}
					
					protected void body() {
						start_sastore( "array", "i" );
						saload( "array", "i" );
						saload( "array", "i" );
						imul();
						sastore();
					}
				} ).
				return_();
		
		ArrayManipulator manipulator = classWriter.< ArrayManipulator >newInstance();
		
		short[] values = new short[] { 1, 2, 3, 4, 5 };
		manipulator.alter( values );
		
		assertArrayEquals(
			new short[] { 1, 4, 9, 16, 25 },
			values );
	}
	
	public final @Test void floatArray() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "FloatSquareRoot" ).extends_( ArrayManipulator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( void.class, "alter" ).
				arg( float[].class, "array" ) ).
				macro( new For() {
					@Override
					protected void init() {
						ideclare( 0, "i" );
					}
					
					protected void test( final String trueLabel ) {
						iload( "i" );
						arraylength( "array" );
						if_icmplt( trueLabel );
					}
					
					protected void step() {
						iinc( "i" );
					}
					
					protected void body() {
						start_fastore( "array", "i" );
						faload( "array", "i" );
						f2d();
						invokestatic( Math.class, method( double.class, "sqrt", double.class ) );
						d2f();
						fastore();
					}
				} ).
				return_();
		
		ArrayManipulator sqrt = classWriter.< ArrayManipulator >newInstance();
		
		float[] values = new float[] { 4f, 9f, 16f, 1024f, 1048576f };
		sqrt.alter( values );
		assertTrue(
			Arrays.equals(
				new float[] { 2f, 3f, 4f, 32f, 1024f },
				values ) );
	}
	
	public final @Test void doubleArray() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "DoubleSquareRoot" ).extends_( ArrayManipulator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( void.class, "alter" ).
				arg( double[].class, "array" ) ).
				
				macro( new For() {
					@Override
					protected void init() {
						ideclare( 0, "i" );
					}
					
					protected void test( final String trueLabel ) {
						iload( "i" );
						arraylength( "array" );
						if_icmplt( trueLabel );
					}
					
					protected void step() {
						iinc( "i" );
					}
					
					protected void body() {
						start_dastore( "array", "i" );
						daload( "array", "i" );
						invokestatic( Math.class, method( double.class, "sqrt", double.class ) );
						dastore();
					}
				} ).
				return_();
		
		ArrayManipulator manipulator = classWriter.< ArrayManipulator >newInstance();
		
		double[] values = new double[] { 1048576d, 1024d, 81d };
		manipulator.alter( values );
		assertTrue(
			Arrays.equals(
				new double[] { 1024d, 32d, 9d },
				values ) );
	}
	
	public final @Test void objectArray() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "ObjectReverse" ).implements_( ObjectArrayManipulator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( void.class, "alter" ).
				arg( Object[].class, "array" ) ).
				macro( new For() {
					protected void init() {
						ideclare( 0, "i" );
					}
					
					protected void test( final String trueLabel ) {
						iload( "i" );
						arraylength( "array" );
						iconst_2();
						idiv();
						if_icmplt( trueLabel );
					}
					
					protected void step() {
						iinc( "i" );
					}
					
					protected void body() {
						ideclare( "swapIndex" );
						
						//swapIndex = array.length - i - 1
						arraylength( "array" );
						iload( "i" );
						isub();
						iconst_1();
						isub();
						istore( "swapIndex" );
						
						adeclare( "tmp" );
						
						//tmp = array[ i ]
						aaload( "array", "i" );
						astore( "tmp" );
						
						//array[ i ] = array[ swapIndex ];
						start_aastore( "array", "i" );
						aaload( "array", "swapIndex" );
						aastore();
						
						//array[ swapIndex ] = tmp
						start_aastore( "array", "swapIndex" );
						aload( "tmp" );
						aastore();
					}
				} ).
				return_();
		
		ObjectArrayManipulator manipulator = classWriter.< ObjectArrayManipulator >newInstance();
		
		String[] values = new String[] { "Hello", "World" };
		manipulator.alter( values );
		assertArrayEquals(
			new String[] { "World", "Hello" },
			values );
	}
	
	public final @Test void arrayMacros() {
		assertTrue(
			Arrays.equals(
				new boolean[] { true, false, false, true },
				makeArray( new boolean[] { true, false, false, true } ) ) );
		assertArrayEquals(
			new byte[] { -1, 10, 20, 30 },
			makeArray( new byte[] { -1, 10, 20, 30 } ) );
		assertArrayEquals(
			new short[] { -1, 10, 20, 30 },
			makeArray( new short[] { -1, 10, 20, 30 } ) );
		assertArrayEquals(
			new char[] { 'h', 'e', 'l', 'l', 'o' },
			makeArray( new char[] { 'h', 'e', 'l', 'l', 'o' } ) );
		assertArrayEquals(
			new int[] { -1, 10, 20, 30, 200 },
			makeArray( new int[] { -1, 10, 20, 30, 200 } ) );
		assertArrayEquals(
			new long[] { 0L, 1L, 2L, 10L, 30L, 100L, Long.MAX_VALUE },
			makeArray( new long[] { 0L, 1L, 2L, 10L, 30L, 100L, Long.MAX_VALUE } ) );
		assertTrue(
			Arrays.equals(
				new float[] { 0F, 1F, 2F, 10F, 30F, 100F },
				makeArray( new float[] { 0F, 1F, 2F, 10F, 30F, 100F } ) ) );
		assertTrue(
			Arrays.equals(
				new double[] { 0D, 1D, 2D, 10D, 30D, 100D },
				makeArray( new double[] { 0D, 1D, 2D, 10D, 30D, 100D } ) ) );		
	}
	
	private static final boolean[] makeArray( final boolean[] array ) {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "BooleanArray" ).implements_( Creator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( Object.class, "create" ) ).
			barray( array ).
			areturn();
		
		Creator arrayCreator = classWriter.< Creator >newInstance();
		return (boolean[])arrayCreator.create();
	}

	private static final byte[] makeArray( final byte[] array ) {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "ByteArray" ).implements_( Creator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( Object.class, "create" ) ).
			barray( array ).
			areturn();
		
		Creator arrayCreator = classWriter.< Creator >newInstance();
		return (byte[])arrayCreator.create();
	}

	private static final char[] makeArray( final char[] array ) {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "CharArray" ).implements_( Creator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( Object.class, "create" ) ).
			carray( array ).
			areturn();
		
		Creator arrayCreator = classWriter.< Creator >newInstance();
		return (char[])arrayCreator.create();
	}
	
	private static final short[] makeArray( final short[] array ) {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "ShortArray" ).implements_( Creator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( Object.class, "create" ) ).
			sarray( array ).
			areturn();
		
		Creator arrayCreator = classWriter.< Creator >newInstance();
		return (short[])arrayCreator.create();
	}
	
	private static final int[] makeArray( final int[] array ) {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "IntArray" ).implements_( Creator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( Object.class, "create" ) ).
			iarray( array ).
			areturn();
		
		Creator arrayCreator = classWriter.< Creator >newInstance();
		return (int[])arrayCreator.create();
	}

	private static final long[] makeArray( final long[] array ) {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "LongArray" ).implements_( Creator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( Object.class, "create" ) ).
			larray( array ).
			areturn();
		
		Creator arrayCreator = classWriter.< Creator >newInstance();
		return (long[])arrayCreator.create();
	}
	
	private static final float[] makeArray( final float[] array ) {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "FloatArray" ).implements_( Creator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( Object.class, "create" ) ).
			farray( array ).
			areturn();
		
		Creator arrayCreator = classWriter.< Creator >newInstance();
		return (float[])arrayCreator.create();
	}
	
	private static final double[] makeArray( final double[] array ) {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "DoubleArray" ).implements_( Creator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( Object.class, "create" ) ).
			darray( array ).
			areturn();
		
		Creator arrayCreator = classWriter.< Creator >newInstance();
		return (double[])arrayCreator.create();
	}
	
	public final @Test void multiArrayTest() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "MultiArray" ).implements_( Creator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( Object.class, "create" ) ).
			iconst_4().
			iconst_3().
			iconst_2().
			iconst_1().
			multianewarray( String[][][][].class, 4 ).
			areturn();
		
		Creator multiArrayCreator = classWriter.< Creator >newInstance();
		assertTrue( multiArrayCreator.create() instanceof String[][][][] );
	}
	
	public final @Test void multiArrayTest2() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "MultiArray" ).implements_( Creator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( Object.class, "create" ) ).
			iconst_4().
			iconst_3().
			iconst_2().
			iconst_1().
			multianewarray( String[][][][].class, 4 ).
			areturn();
		
		Creator multiArrayCreator = classWriter.< Creator >newInstance();
		assertTrue( multiArrayCreator.create() instanceof String[][][][] );
	}
	
	public interface Creator {
		public abstract Object create();
	}
	
	public interface IntFunction {
		public abstract int process( final int[] array );
	}
	
	public static abstract class ArrayManipulator {
		public void alter( final boolean[] array ) {
			throw new UnsupportedOperationException();
		}

		public void alter( final char[] array ) {
			throw new UnsupportedOperationException();
		}
		
		public void alter( final short[] array ) {
			throw new UnsupportedOperationException();
		}
		
		public void alter( final long[] array ) {
			throw new UnsupportedOperationException();
		}
		
		public void alter( final float[] array ) {
			throw new UnsupportedOperationException();
		}
		
		public void alter( final double[] array ) {
			throw new UnsupportedOperationException();
		}
		
		public void alter( final Object[] array ) {
			throw new UnsupportedOperationException();
		}
	}
}
