package net.dougqh.jak.jvm.assembler.api;

import static net.dougqh.jak.Jak.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;

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
	
	public final @Test void integerArrayLoad() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "Sum" ).implements_( IntFunction.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().method( int.class, "process" ).
				arg( int[].class, "array" ) ).
			
			//sum = 0
			iconst_0().
			istore( "sum" ).
			
			//i = 0
			iconst_0().
			istore( "i" ).
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
		assertEquals(
			59,
			sum.process( new int[] { 1, 8, 50 } ) );
	}
	
	public final @Test void longArrays() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "Rotate" ).implements_( LongArrayManipulator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( void.class, "alter" ).
				arg( long[].class, "array" ) ).
				
			//tmp = array[ array.length - 1 ];
			aload( "array" ).
			dup().
			arraylength().
			iconst_1().
			isub().
			laload().
			
			lstore( "tmp" ).
			
			//i = array.length - 2
			aload( "array" ).
			arraylength().
			iconst_2().
			isub().
			istore( "i" ).
			goto_( "test" ).
			
			label( "loop" ).
			//array[ i + 1 ] = array[ i ];
			aload( "array" ).
			iload( "i" ).
			iconst_1().
			iadd().
			aload( "array" ).
			iload( "i" ).
			laload().
			lastore().
			
			//--i;
			iinc( "i", -1 ).
			
			label( "test" ).
			//if ( i >= 0 ) goto loop
			iload( "i" ).
			ifge( "loop" ).
			
			//array[ 0 ] = tmp;
			aload( "array" ).
			iconst_0().
			lload( "tmp" ).
			lastore().
			
			return_();
		
		LongArrayManipulator manipulator = classWriter.< LongArrayManipulator >newInstance();
		
		long[] values = new long[] { 10L, 20L, 30L, 40L, 50L };
		manipulator.alter( values );
		
		assertArrayEquals(
			new long[] { 50L, 10L, 20L, 30L, 40L },
			values );
	}
	
	public final @Test void booleanArrays() {
		JvmClassWriter classWriter = new JvmWriter().define( 
			public_().final_().class_( "BooleanFlipper" ).implements_( BooleanArrayManipulator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( void.class, "alter" ).
				arg( boolean[].class, "array" ) ).
			
			//i = 0
			iconst_0().
			istore( "i" ).
			goto_( "test" ).
			
			label( "loop" ).
			//array[ i ] = ! array[ i ];
			aload( "array" ).
			iload( "i" ).
			aload( "array" ).
			iload( "i" ).
			baload().
			inot().
			bastore().
			
			//++i
			iinc( "i" ).
			
			label( "test" ).
			iload( "i" ).
			aload( "array" ).
			arraylength().
			if_icmplt( "loop" ).
			
			return_();
		
		BooleanArrayManipulator flipper = classWriter.< BooleanArrayManipulator >newInstance();
		
		boolean[] values = new boolean[] { false, true, false, false, true };
		flipper.alter( values );
		
		assertTrue(
			Arrays.equals(
				new boolean[] { true, false, true, true, false },
				values ) );
	}
	
	public final @Test void charArrays() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "CharIncrementer" ).implements_( CharArrayManipulator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( void.class, "alter" ).
				arg( char[].class, "array" ) ).
				
			//i = 0
			iconst_0().
			istore( "i" ).
			goto_( "test" ).
			
			label( "loop" ).
			//array[ i ] = array[ i ] + 1
			aload( "array" ).
			iload( "i" ).
			aload( "array" ).
			iload( "i" ).
			caload().
			iconst_1().
			iadd().
			castore().
			
			//++i;
			iinc( "i" ).
			
			label( "test" ).
			//if ( i < array.length ) goto loop
			iload( "i" ).
			aload( "array" ).
			arraylength().
			if_icmplt( "loop" ).
			
			return_();
		
		CharArrayManipulator manipulator = classWriter.< CharArrayManipulator >newInstance();
		
		char[] values = new char[] { 'h', 'e', 'l', 'l', 'o' };
		manipulator.alter( values );
		
		assertArrayEquals(
			new char[] { 'i', 'f', 'm', 'm', 'p' },
			values );
	}
	
	public final @Test void shortArray() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "ShortSquared" ).implements_( ShortArrayManipulator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( void.class, "alter" ).
				arg( short[].class, "array" ) ).
			//i = 0
			iconst( 0 ).
			istore( "i" ).
			goto_( "test" ).
			//loop
			label( "loop" ).
			//array[ i ] *= array[ i ];
			aload( "array" ).
			iload( "i" ).
			aload( "array" ).
			iload( "i" ).
			saload().
			aload( "array" ).
			iload( "i" ).
			saload().
			imul().
			i2s().
			sastore().
			//++i
			iinc( "i" ).
			//test
			label( "test" ).
			iload( "i" ).
			aload( "array" ).
			arraylength().
			if_icmplt( "loop" ).
			//return
			return_();
		
		ShortArrayManipulator manipulator = classWriter.< ShortArrayManipulator >newInstance();
		
		short[] values = new short[] { 1, 2, 3, 4, 5 };
		manipulator.alter( values );
		
		assertArrayEquals(
			new short[] { 1, 4, 9, 16, 25 },
			values );
	}
	
	public final @Test void floatArray() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "FloatSquareRoot" ).implements_( FloatArrayManipulator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( void.class, "alter" ).
				arg( float[].class, "array" ) ).
			//i=0
			iconst( 0 ).
			istore( "i" ).
			goto_( "test" ).
			//loop
			label( "loop" ).
			//array[ i ] = (float)Math.sqrt( array[ i ] )
			aload( "array" ).
			iload( "i" ).
			aload( "array" ).
			iload( "i" ).
			faload().
			f2d().
			invokestatic( Math.class, method( double.class, "sqrt", double.class ) ).
			d2f().
			fastore().
			//inc
			iinc( "i" ).
			//test
			label( "test" ).
			iload( "i" ).
			aload( "array" ).
			arraylength().
			if_icmplt( "loop" ).
			//return
			return_();
		
		FloatArrayManipulator manipulator = classWriter.< FloatArrayManipulator >newInstance();
		
		float[] values = new float[] { 4f, 9f, 16f, 1024f, 1048576f };
		manipulator.alter( values );
		assertTrue(
			Arrays.equals(
				new float[] { 2f, 3f, 4f, 32f, 1024f },
				values ) );
	}
	
	public final @Test void doubleArray() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "DoubleSquareRoot" ).implements_( DoubleArrayManipulator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( void.class, "alter" ).
				arg( double[].class, "array" ) ).
			//i=0
			iconst( 0 ).
			istore( "i" ).
			goto_( "test" ).
			//loop
			label( "loop" ).
			//array[ i ] = Math.sqrt( array[ i ] )
			aload( "array" ).
			iload( "i" ).
			aload( "array" ).
			iload( "i" ).
			daload().
			invokestatic( Math.class, method( double.class, "sqrt", double.class ) ).
			dastore().
			//inc
			iinc( "i" ).
			//test
			label( "test" ).
			iload( "i" ).
			aload( "array" ).
			arraylength().
			if_icmplt( "loop" ).
			//return
			return_();
		
		DoubleArrayManipulator manipulator = classWriter.< DoubleArrayManipulator >newInstance();
		
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
			//i=0
			iconst_0().
			istore( "i" ).
			goto_( "test" ).
			//loop
			label( "loop" ).
			//tmp = array[ i ]
			aload( "array" ).
			iload( "i" ).
			aaload().
			astore( "tmp" ).
			//array[ i ] = array[ array.length - 1 ]
			aload( "array" ).
			iload( "i" ).
			aload( "array" ).
			aload( "array" ).
			arraylength().
			iconst_1().
			isub().
			aaload().
			aastore().
			//array[ array.length - 1 ] = tmp
			aload( "array" ).
			aload( "array" ).
			arraylength().
			iconst_1().
			isub().
			aload( "tmp" ).
			aastore().
			//inc
			iinc( "i" ).
			//test
			label( "test" ).
			//if ( i < array.length < 2 ) goto loop
			iload( "i" ).
			aload( "array" ).
			arraylength().
			iconst_2().
			idiv().
			if_icmplt( "loop" ).
			//return
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
	
	public interface BooleanArrayManipulator {
		public abstract void alter( final boolean[] array );
	}
	
	public interface CharArrayManipulator {
		public abstract void alter( final char[] array );
	}
	
	public interface ShortArrayManipulator {
		public abstract void alter( final short[] array );
	}
	
	public interface LongArrayManipulator {
		public abstract void alter( final long[] array );
	}
	
	public interface FloatArrayManipulator {
		public abstract void alter( final float[] array );
	}
	
	public interface DoubleArrayManipulator {
		public abstract void alter( final double[] array );
	}
	
	public interface ObjectArrayManipulator {
		public abstract void alter( final Object[] array );
	}
}
