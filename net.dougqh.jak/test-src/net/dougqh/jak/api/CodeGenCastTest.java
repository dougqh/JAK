package net.dougqh.jak.api;

import static net.dougqh.jak.JavaAssembler.*;
import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

import net.dougqh.jak.JavaClassWriter;
import net.dougqh.jak.JavaCodeWriter;

import org.junit.Test;

public final class CodeGenCastTest {
	public final @Test void intCasts() {
		JavaClassWriter writer = define( public_().static_().final_().class_( "IntCasts" ) );
		
		castMethod( writer, int.class, byte.class ).
			iload_0().
			i2b().
			ireturn();
		
		castMethod( writer, int.class, char.class ).
			iload_0().
			i2c().
			ireturn();
		
		castMethod( writer, int.class, short.class ).
			iload_0().
			i2s().
			ireturn();
		
		castMethod( writer, int.class, float.class ).
			iload_0().
			i2f().
			freturn();
		
		castMethod( writer, int.class, long.class ).
			iload_0().
			i2l().
			lreturn();
		
		castMethod( writer, int.class, double.class ).
			iload_0().
			i2d().
			dreturn();
		
		Class< ? > intCasts = writer.load();
		assertEquals( Byte.valueOf( (byte)30 ), convertInt( intCasts, 30, Byte.class ) );
		assertEquals( Character.valueOf( (char)32 ), convertInt( intCasts, 32, Character.class ) );
		assertEquals( Short.valueOf( (short)314 ), convertInt( intCasts, 314, Short.class ) );
		assertEquals( Float.valueOf( 30F ), convertInt( intCasts, 30, Float.class ) );
		assertEquals( Long.valueOf( 50000L ), convertInt( intCasts, 50000, Long.class ) );
		assertEquals( Double.valueOf( 2718F ), convertInt( intCasts, 2718, Double.class ) );
	}
	
	public final @Test void longCasts() {
		JavaClassWriter writer = define( public_().static_().final_().class_( "LongCasts" ) );
		
		castMethod( writer, long.class, int.class ).
			lload_0().
			l2i().
			ireturn();
		
		castMethod( writer, long.class, float.class ).
			lload_0().
			l2f().
			freturn();
		
		castMethod( writer, long.class, double.class ).
			lload_0().
			l2d().
			dreturn();
		
		Class< ? > longCasts = writer.load();
		assertEquals( Integer.valueOf( 30 ), convertLong( longCasts, 30L, int.class ) );
		assertEquals( Float.valueOf( 30F ), convertLong( longCasts, 30L, float.class ) );
		assertEquals( Double.valueOf( 30D ), convertLong( longCasts, 30L, double.class ) );
	}
	
	public final @Test void floatCasts() {
		JavaClassWriter writer = define( public_().static_().final_().class_( "FloatCasts" ) );
		
		castMethod( writer, float.class, int.class ).
			fload( 0 ).
			f2i().
			ireturn();
		
		castMethod( writer, float.class, long.class ).
			fload_0().
			f2l().
			lreturn();
		
		castMethod( writer, float.class, double.class ).
			fload( 0 ).
			f2d().
			dreturn();
		
		Class< ? > floatCasts = writer.load();
		assertEquals( Integer.valueOf( 30 ), convertFloat( floatCasts, 30F, int.class ) );
		assertEquals( Long.valueOf( 30L ), convertFloat( floatCasts, 30F, long.class ) );
		assertEquals( Double.valueOf( 30D ), convertFloat( floatCasts, 30F, double.class ) );
	}
	
	public final @Test void doubleCasts() {
		JavaClassWriter writer = define( public_().static_().final_().class_( "DoubleCasts" ) );
		
		castMethod( writer, double.class, int.class ).
			dload( 0 ).
			d2i().
			ireturn();
		
		castMethod( writer, double.class, long.class ).
			dload( 0 ).
			d2l().
			lreturn();
		
		castMethod( writer, double.class, float.class ).
			dload( 0 ).
			d2f().
			freturn();
		
		Class< ? > doubleCasts = writer.load();
		assertEquals( Integer.valueOf( 30 ), convertDouble( doubleCasts, 30D, int.class ) );
		assertEquals( Long.valueOf( 30L ), convertDouble( doubleCasts, 30D, long.class ) );
		assertEquals( Float.valueOf( 30F ), convertDouble( doubleCasts, 30D, float.class ) );
	}
	
	public final @Test void objectCast() {
		Type Serializer_String = type( Serializer.class ).of( String.class ).make();
		
		JavaClassWriter classWriter = define(
			public_().final_().class_( "StringSerializer" ).implements_( Serializer_String ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( String.class, "serialize", Object.class ) ).
			this_().
			aload_1().
			checkcast( String.class ).
			invokevirtual( classWriter.thisType(), method( String.class, "serialize", String.class ) ).
			areturn();
		
		classWriter.define(
			public_().final_().method( String.class, "serialize", String.class ) ).
			aload_1().
			areturn();
		
		Serializer< String > serializer = classWriter.< Serializer< String > >newInstance();
		assertEquals(
			"Hello World",
			serializer.serialize( "Hello World" ) );
	}
	
	public final @Test void arrayCast() {
		Type Serializer_intArray = type( Serializer.class ).of( int[].class ).make();
		
		JavaClassWriter classWriter = define(
			public_().final_().class_( "IntArraySerializer" ).implements_( Serializer_intArray ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( String.class, "serialize", Object.class ) ).
			this_().
			aload_1().
			checkcast( int[].class ).
			invokevirtual( classWriter.thisType(), method( String.class, "serialize", int[].class ) ).
			areturn();
		
		classWriter.define(
			public_().final_().method( String.class, "serialize", int[].class ) ).
			aload_1().
			invokestatic( Arrays.class, method( String.class, "toString", int[].class ) ).
			areturn();
		
		Serializer< int[] > serializer = classWriter.< Serializer< int[] > >newInstance();
		assertEquals(
			"[0, 1, 2, 3, 4]",
			serializer.serialize( new int[] { 0, 1, 2, 3, 4 } ) );
	}
	
	public final @Test void instanceof_() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "NumberPredicate" ).implements_( Predicate.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( boolean.class, "isAcceptable", Object.class ) ).
			aload_1().
			instanceof_( Number.class ).
			ireturn();
		
		Predicate predicate = classWriter.< Predicate >newInstance();
		assertTrue( predicate.isAcceptable( 2 ) );
		assertFalse( predicate.isAcceptable( "Hello" ) );
		assertTrue( predicate.isAcceptable( 30L ) );
		assertFalse( predicate.isAcceptable( new int[] {} ) );
	}
	
	static final JavaCodeWriter castMethod(
		final JavaClassWriter classWriter,
		final Class< ? > fromClass,
		final Class< ? > toClass )
	{
		return classWriter.define(
			public_().static_().final_().
				method( toClass, getMethodName( fromClass, toClass ) ).args( fromClass ) );
	}

	private static String getMethodName(
		final Class< ? > fromClass,
		final Class< ? > toClass )
	{
		char fromClassFirstChar = fromClass.getSimpleName().charAt( 0 );
		char toClassFirstChar = toClass.getSimpleName().charAt( 0 );
		return ( fromClassFirstChar + "2" + toClassFirstChar ).toLowerCase();
	}
	
	private static final < T > T convertInt(
		final Class< ? > theClass,
		final int value,
		final Class< T > toClass )
	{
		return convertTo( theClass, value, int.class, toClass );
	}
	
	private static final < T > T convertLong(
		final Class< ? > theClass,
		final long value,
		final Class< T > toClass )
	{
		return convertTo( theClass, value, long.class, toClass );
	}
	
	private static final < T > T convertFloat(
		final Class< ? > theClass,
		final float value,
		final Class< T > toClass )
	{
		return convertTo( theClass, value, float.class, toClass );
	}
	
	private static final < T > T convertDouble(
		final Class< ? > theClass,
		final double value,
		final Class< T > toClass )
	{
		return convertTo( theClass, value, double.class, toClass );
	}
	
	@SuppressWarnings( "unchecked" )
	private static final < T > T convertTo(
		final Class< ? > theClass,
		final Object value,
		final Class< ? > fromClass,
		final Class< T > toClass )
	{
		try {
			return (T)getMethod( theClass, fromClass, toClass ).invoke(
				null,
				value );
		}  catch ( IllegalAccessException e ) {
			throw new IllegalStateException( e );
		} catch ( InvocationTargetException e ) {
			throw new IllegalArgumentException( e );
		}
	}
	
	private static final Method getMethod(
		final Class< ? > theClass,
		final Class< ? > fromClass,
		final Class< ? > toClass )
	{
		try {
			return theClass.getMethod(
				getMethodName( fromClass, toClass ),
				fromClass );
		} catch ( NoSuchMethodException e ) {
			throw new IllegalArgumentException( e );
		}
	}
	
	public static interface Serializer< T > {
		public abstract String serialize( final T value );
	}
	
	public static interface Predicate {
		public abstract boolean isAcceptable( final Object object );
	}
}
