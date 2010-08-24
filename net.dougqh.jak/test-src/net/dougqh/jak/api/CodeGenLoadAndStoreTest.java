package net.dougqh.jak.api;

import static net.dougqh.jak.JavaAssembler.*;
import static org.junit.Assert.*;
import net.dougqh.jak.JavaClassWriter;

import org.junit.Test;

public final class CodeGenLoadAndStoreTest {
	public final @Test void intLoadAndStore() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "IntStore" ).implements_( IntCalculator.class ) );
		
		classWriter.defineDefaultConstructor();
					
		//Slightly twisted way to test - brute force rotate the int-s using a temp slot 4
		//then perform a mix of operations to (roughly) verify that the order is correct
		//with a single return value.
		classWriter.define(
			public_().final_().
				method( int.class, "calculate", int.class, int.class, int.class, int.class ) ).
			iload( 4 ).
			istore( 5 ).
			iload( 3 ).
			istore( 4 ).
			iload( 2 ).
			istore( 3 ).
			iload( 1 ).
			istore( 2 ).
			iload( 5 ).
			istore( 1 ).
			iload( 1 ).
			iload( 2 ).
			isub().
			iload( 3 ).
			imul().
			iload( 4 ).
			iadd().
			ireturn();
		
		IntCalculator intCalculator = classWriter.< IntCalculator >newInstance();
		assertEquals(
			9,
			intCalculator.calculate( 1, 2, 3, 4 ) );
	}
	
	public final @Test void floatLoadAndStore() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "FloatStore" ).implements_( FloatCalculator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		//Slightly twisted way to test - brute force rotate the float-s using a temp slot 4
		//then perform a mix of operations to (roughly) verify that the order is correct
		//with a single return value.
		classWriter.define(
			public_().final_().
				method( float.class, "calculate", float.class, float.class, float.class, float.class ) ).
			fload( 4 ).
			fstore( 5 ).
			fload( 3 ).
			fstore( 4 ).
			fload( 2 ).
			fstore( 3 ).
			fload( 1 ).
			fstore( 2 ).
			fload( 5 ).
			fstore( 1 ).
			fload( 1 ).
			fload( 2 ).
			fsub().
			fload( 3 ).
			fmul().
			fload( 4 ).
			fadd().
			freturn();
		
		FloatCalculator floatCalculator = classWriter.< FloatCalculator >newInstance();
		assertEquals(
			9.0f,
			floatCalculator.calculate( 1f, 2f, 3f, 4f ),
			0.001f );
	}
	
	public final @Test void longStoreOddSlots() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "LongStore" ).implements_( LongOddSlotCalculator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		//Slightly twisted way to test - brute force rotate the long-s using a temp slot 4
		//then perform a mix of operations to (roughly) verify that the order is correct
		//with a single return value.
		classWriter.define(
			public_().final_().
				method( long.class, "calculate", long.class, long.class, long.class, long.class ) ).
			lload( 7 ).
			lstore( 9 ).
			lload( 5 ).
			lstore( 7 ).
			lload( 3 ).
			lstore( 5 ).
			lload( 1 ).
			lstore( 3 ).
			lload( 9 ).
			lstore( 1 ).
			lload( 1 ).
			lload( 3 ).
			lsub().
			lload( 5 ).
			lmul().
			lload( 7 ).
			ladd().
			lreturn();
		
		LongOddSlotCalculator longCalculator = classWriter.< LongOddSlotCalculator >newInstance();
		assertEquals(
			9L,
			longCalculator.calculate( 1L, 2L, 3L, 4L ) );
	}

	public final @Test void longStoreEvenSlots() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "LongStore" ).implements_( LongEvenSlotCalculator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		//Slightly twisted way to test - brute force rotate the long-s using a temp slot 4
		//then perform a mix of operations to (roughly) verify that the order is correct
		//with a single return value.
		classWriter.define(
			public_().final_().
				method( long.class, "calculate", int.class, long.class, long.class, long.class, long.class ) ).
			lload( 8 ).
			lstore( 10 ).
			lload( 6 ).
			lstore( 8 ).
			lload( 4 ).
			lstore( 6 ).
			lload( 2 ).
			lstore( 4 ).
			lload( 10 ).
			lstore( 2 ).
			lload( 2 ).
			lload( 4 ).
			lsub().
			lload( 6 ).
			lmul().
			lload( 8 ).
			ladd().
			lreturn();
		
		LongEvenSlotCalculator longCalculator = classWriter.< LongEvenSlotCalculator >newInstance();
		assertEquals(
			9L,
			longCalculator.calculate( Integer.MIN_VALUE, 1L, 2L, 3L, 4L ) );
	}
	
	public final @Test void doubleStoreOddSlots() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "DoubleStore" ).implements_( DoubleOddSlotCalculator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		//Slightly twisted way to test - brute force rotate the double-s using a temp slot 4
		//then perform a mix of operations to (roughly) verify that the order is correct
		//with a single return value.
		classWriter.define(
			public_().final_().
				method( double.class, "calculate", double.class, double.class, double.class, double.class ) ).
			dload( 7 ).
			dstore( 9 ).
			dload( 5 ).
			dstore( 7 ).
			dload( 3 ).
			dstore( 5 ).
			dload( 1 ).
			dstore( 3 ).
			dload( 9 ).
			dstore( 1 ).
			dload( 1 ).
			dload( 3 ).
			dsub().
			dload( 5 ).
			dmul().
			dload( 7 ).
			dadd().
			dreturn();
		
		DoubleOddSlotCalculator doubleCalculator = classWriter.< DoubleOddSlotCalculator >newInstance();
		assertEquals(
			9D,
			doubleCalculator.calculate( 1D, 2D, 3D, 4D ),
			0.001D );
	}
	
	public final @Test void doubleStoreEvenSlots() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "DoubleStore" ).implements_( DoubleEvenSlotCalculator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		//Slightly twisted way to test - brute force rotate the double-s using a temp slot 4
		//then perform a mix of operations to (roughly) verify that the order is correct
		//with a single return value.
		classWriter.define(
			public_().final_().
				method( double.class, "calculate", int.class, double.class, double.class, double.class, double.class ) ).
			dload( 8 ).
			dstore( 10 ).
			dload( 6 ).
			dstore( 8 ).
			dload( 4 ).
			dstore( 6 ).
			dload( 2 ).
			dstore( 4 ).
			dload( 10 ).
			dstore( 2 ).
			dload( 2 ).
			dload( 4 ).
			dsub().
			dload( 6 ).
			dmul().
			dload( 8 ).
			dadd().
			dreturn();
		
		DoubleEvenSlotCalculator doubleCalculator = classWriter.< DoubleEvenSlotCalculator >newInstance();
		assertEquals(
			9D,
			doubleCalculator.calculate( Integer.MIN_VALUE, 1D, 2D, 3D, 4D ),
			0.01D );
	}
	
	public final @Test void objectLoad() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "StringConcatenator" ).implements_( StringCalculator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method(
				String.class, "calculate",
				String.class, String.class, String.class, String.class ) ).
			//new StringBuilder( 128 )
			new_( StringBuilder.class ).
			dup().
			iconst( 128 ).
			invokespecial( StringBuilder.class, init( int.class ) ).
			//builder.append args...
			aload( 1 ).
			invokevirtual( StringBuilder.class, method( StringBuilder.class, "append", String.class ) ).
			aload( 2 ).
			invokevirtual( StringBuilder.class, method( StringBuilder.class, "append", String.class ) ).
			aload( 3 ).
			invokevirtual( StringBuilder.class, method( StringBuilder.class, "append", String.class ) ).
			aload( 4 ).
			invokevirtual( StringBuilder.class, method( StringBuilder.class, "append", String.class ) ).
			//return builder.toString()
			invokevirtual( StringBuilder.class, method( String.class, "toString" ) ).
			areturn();
		
		StringCalculator calculator = classWriter.< StringCalculator >newInstance();
		assertEquals(
			"AlphaBetaGammaDelta",
			calculator.calculate( "Alpha", "Beta", "Gamma", "Delta" ) );
	}
	
	public final @Test void objectLoadAndStore() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "ObjectArrayMixer" ).implements_( ObjectArrayManipulator.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( void.class, "alter", Object[].class ) ).
			//tmp0 = array[ 0 ]
			aload_1().
			iconst_0().
			aaload().
			astore( "tmp0" ).
			//tmp1 = array[ 1 ]
			aload_1().
			iconst_1().
			aaload().
			astore( "tmp1" ).
			//tmp2 = array[ 2 ]
			aload_1().
			iconst_2().
			aaload().
			astore( "tmp2" ).
			//tmp3 = array[ 3 ]
			aload_1().
			iconst_3().
			aaload().
			astore( "tmp3" ).
			//array[ 0 ] = tmp1
			aload_1().
			iconst_0().
			aload( "tmp1" ).
			aastore().
			//array[ 1 ] = tmp3
			aload_1().
			iconst_1().
			aload( "tmp3" ).
			aastore().
			//array[ 2 ] = tmp0
			aload_1().
			iconst_2().
			aload( "tmp0" ).
			aastore().
			//array[ 3 ] = tmp2
			aload_1().
			iconst_3().
			aload( "tmp2" ).
			aastore().
			//return
			return_();
		
		ObjectArrayManipulator manipulator = classWriter.< ObjectArrayManipulator >newInstance();
		
		String[] values = new String[] { "foo", "bar", "baz", "quux" };
		manipulator.alter( values );
		assertArrayEquals(
			new String[] { "bar", "quux", "foo", "baz" },
			values );
	}
	
	public final @Test void intSlotZero() {
		JavaClassWriter classWriter = define( public_().final_().class_( "Int" ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( clinit() ).
			iconst( 512 ).
			istore( "tmp" ).
			iload( "tmp" ).
			putstatic( ConstantHolder.class, field( int.class, "INT" ) ).
			return_();
		
		classWriter.newInstance();
		
		assertEquals( 512, ConstantHolder.INT );
	}
	
	public final @Test void floatSlotZero() {
		JavaClassWriter classWriter = define( public_().final_().class_( "Float" ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( clinit() ).
			fconst( 20f ).
			fstore( "tmp" ).
			fload( "tmp" ).
			putstatic( ConstantHolder.class, field( float.class, "FLOAT" ) ).
			return_();
		
		classWriter.newInstance();
		
		assertEquals( 20f, ConstantHolder.FLOAT, 0.01F );
	}
	
	public final @Test void longSlotZero() {
		JavaClassWriter classWriter = define( public_().final_().class_( "Long" ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( clinit() ).
			lconst( Long.MIN_VALUE ).
			lstore( "tmp" ).
			lload( "tmp" ).
			putstatic( ConstantHolder.class, field( long.class, "LONG" ) ).
			return_();
		
		classWriter.newInstance();
		
		assertEquals( Long.MIN_VALUE, ConstantHolder.LONG );
	}
	
	public final @Test void doubleSlotZero() {
		JavaClassWriter classWriter = define( public_().final_().class_( "Double" ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( clinit() ).
			dconst( Double.MIN_VALUE ).
			dstore( "tmp" ).
			dload( "tmp" ).
			putstatic( ConstantHolder.class, field( double.class, "DOUBLE" ) ).
			return_();
		
		classWriter.newInstance();
		
		assertEquals( Double.MIN_VALUE, ConstantHolder.DOUBLE, 0.01D );
	}
	
	public final @Test void objectSlotZero() {
		JavaClassWriter classWriter = define( public_().final_().class_( "Object" ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( clinit() ).
			ldc( "Hello World" ).
			astore( "tmp" ).
			aload( "tmp" ).
			putstatic( ConstantHolder.class, "STRING" ).
			ldc( "Good Bye World" ).
			astore( "tmp2" ).
			aload( "tmp2" ).
			putstatic( ConstantHolder.class, "STRING2" ).
			return_();
		
		classWriter.newInstance();
		
		assertEquals( "Hello World", ConstantHolder.STRING );
		assertEquals( "Good Bye World", ConstantHolder.STRING2 );
	}
	
	public static interface IntCalculator {
		public abstract int calculate(
			final int value0,
			final int value1,
			final int value2, 
			final int value3 );
	}
	
	public static interface LongOddSlotCalculator {
		public abstract long calculate(
			final long value0,
			final long value1,
			final long value2,
			final long value3 );
	}

	public static interface LongEvenSlotCalculator {
		public abstract long calculate(
			final int unused,
			final long value0,
			final long value1,
			final long value2,
			final long value3 );
	}
	
	public static interface FloatCalculator {
		public abstract float calculate(
			final float value0,
			final float value1,
			final float value2,
			final float value3 );
	}

	public static interface DoubleOddSlotCalculator {
		public abstract double calculate(
			final double value0,
			final double value1,
			final double value2,
			final double value3 );
	}

	public static interface DoubleEvenSlotCalculator {
		public abstract double calculate(
			final int unused,
			final double value0,
			final double value1,
			final double value2,
			final double value3 );
	}
	
	public static interface StringCalculator {
		public abstract String calculate(
			final String value0,
			final String value1,
			final String value2,
			final String value3 );
	}
	
	public static interface ObjectArrayManipulator {
		public abstract void alter(
			final Object[] array );
	}
	
	public static final class ConstantHolder {
		public static int INT = 0;
		public static float FLOAT = 0;
		public static double DOUBLE = 0;
		public static long LONG = 0;
		public static String STRING = null;
		public static String STRING2 = null;
	}
}
