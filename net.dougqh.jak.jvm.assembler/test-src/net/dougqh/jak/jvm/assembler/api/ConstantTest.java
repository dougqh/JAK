package net.dougqh.jak.jvm.assembler.api;

import static net.dougqh.jak.Jak.*;
import static org.junit.Assert.*;
import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;

import org.junit.Test;

public final class ConstantTest {
	public final @Test void booleanConstants() {
		assertTrue( makeBoolean( true ) );
		assertFalse( makeBoolean( false ) );
	}
	
	private static final boolean makeBoolean( final boolean value ) {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "BooleanConstImpl" ).implements_( BooleanConst.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().final_().method( boolean.class, "get" ) ).
			iconst( value ).
			ireturn();
		
		BooleanConst booleanConst = classWriter.< BooleanConst >newInstance();
		return booleanConst.get();
	}
	
	public final @Test void charConstants() {
		assertEquals( '\0', makeChar( '\0' ) );
		assertEquals( 'a', makeChar( 'a' ) );
		assertEquals( ' ', makeChar( ' ' ) );
	}
	
	private static final char makeChar( final char value ) {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "CharConstImpl" ).implements_( CharConst.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().final_().method( char.class, "get" ) ).
			iconst( value ).
			ireturn();
		
		CharConst charConst = classWriter.< CharConst >newInstance();
		return charConst.get();
	}
	
	public final @Test void byteConstants() {
		assertEquals( Byte.MIN_VALUE, makeByte( Byte.MIN_VALUE ) );
		assertEquals( -1, makeByte( (byte)-1 ) );
		assertEquals( 0, makeByte( (byte)0 ) );
		assertEquals( 1, makeByte( (byte)1 ) );
		assertEquals( 2, makeByte( (byte)2 ) );
		assertEquals( 3, makeByte( (byte)3 ) );
		assertEquals( 4, makeByte( (byte)4 ) );
		assertEquals( 5, makeByte( (byte)5 ) );		
		assertEquals( Byte.MAX_VALUE, makeByte( Byte.MAX_VALUE ) );
	}
	
	private static final byte makeByte( final byte value ) {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "ByteConstImpl" ).implements_( ByteConst.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().final_().method( byte.class, "get" ) ).
			iconst( value ).
			ireturn();
		
		ByteConst byteConst = classWriter.< ByteConst >newInstance();
		return byteConst.get();
	}
	
	public final @Test void shortConstants() {
		assertEquals( Short.MIN_VALUE, makeShort( Short.MIN_VALUE ) );
		assertEquals( Byte.MIN_VALUE, makeShort( Byte.MIN_VALUE ) );
		assertEquals( -1, makeShort( (short)-1 ) );
		assertEquals( 0, makeShort( (short)0 ) );
		assertEquals( 1, makeShort( (short)1 ) );
		assertEquals( 2, makeShort( (short)2 ) );
		assertEquals( 3, makeShort( (short)3 ) );
		assertEquals( 4, makeShort( (short)4 ) );
		assertEquals( 5, makeShort( (short)5 ) );
		assertEquals( Byte.MAX_VALUE, makeShort( Byte.MAX_VALUE ) );
		assertEquals( Short.MAX_VALUE, makeShort( Short.MAX_VALUE ) );
	}
	
	private static final short makeShort( final short value ) {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "ShortConstImpl" ).implements_( ShortConst.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().final_().method( short.class,"get" ) ).
			iconst( value ).
			ireturn();
		
		ShortConst shortConst = classWriter.< ShortConst >newInstance();
		return shortConst.get();
	}
	
	public final @Test void integerConstants() {
		assertEquals( Integer.MIN_VALUE, makeInt( Integer.MIN_VALUE ) );
		assertEquals( Short.MIN_VALUE, makeInt( Short.MIN_VALUE ) );
		assertEquals( Byte.MIN_VALUE, makeInt( Byte.MIN_VALUE ) );
		assertEquals( -1, makeInt( -1 ) );
		assertEquals( 0, makeInt( 0 ) );
		assertEquals( 1, makeInt( 1 ) );
		assertEquals( 2, makeInt( 2 ) );
		assertEquals( 3, makeInt( 3 ) );
		assertEquals( 4, makeInt( 4 ) );
		assertEquals( 5, makeInt( 5 ) );
		assertEquals( Byte.MAX_VALUE, makeInt( Byte.MAX_VALUE ) );
		assertEquals( Short.MAX_VALUE, makeInt( Short.MAX_VALUE ) );
		assertEquals( Integer.MAX_VALUE, makeInt( Integer.MAX_VALUE ) );
	}
	
	private static final int makeInt( final int value ) {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "IntConstImpl" ).implements_( IntConst.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( int.class, "get" ) ).
			iconst( value ).
			ireturn();
		
		IntConst intConst = classWriter.< IntConst >newInstance();
		return intConst.get();
	}
	
	public final @Test void longConstants() {
		assertEquals( Long.MIN_VALUE, makeLong( Long.MIN_VALUE ) );
		assertEquals( Integer.MIN_VALUE, makeLong( Integer.MIN_VALUE ) );
		assertEquals( Short.MIN_VALUE, makeLong( Short.MIN_VALUE ) );
		assertEquals( Byte.MIN_VALUE, makeLong( Byte.MIN_VALUE ) );
		assertEquals( -1, makeLong( -1 ) );
		assertEquals( 0, makeLong( 0 ) );
		assertEquals( 1, makeLong( 1 ) );
		assertEquals( 2, makeLong( 2 ) );
		assertEquals( 3, makeLong( 3 ) );
		assertEquals( 4, makeLong( 4 ) );
		assertEquals( 5, makeLong( 5 ) );
		assertEquals( Byte.MAX_VALUE, makeLong( Byte.MAX_VALUE ) );
		assertEquals( Short.MAX_VALUE, makeLong( Short.MAX_VALUE ) );
		assertEquals( Integer.MAX_VALUE, makeLong( Integer.MAX_VALUE ) );
		assertEquals( Long.MAX_VALUE, makeLong( Long.MAX_VALUE ) );
	}
	
	private static long makeLong( final long value ) {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "LongConstImpl" ).implements_( LongConst.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( long.class, "get" ) ).
			lconst( value ).
			lreturn();
		
		LongConst longConst = classWriter.< LongConst >newInstance();
		return longConst.get();
	}
	
	public final @Test void floatConstants() {
		assertEquals( Float.NaN, makeFloat( Float.NaN ), 0.01F );
		assertEquals( Float.NEGATIVE_INFINITY, makeFloat( Float.NEGATIVE_INFINITY ), 0.01F );
		assertEquals( Float.MIN_VALUE, makeFloat( Float.MIN_VALUE ), 0.01F );
		assertEquals( 0F, makeFloat( 0F ), 0.01F );
		assertEquals( 1F, makeFloat( 1F ), 0.01F );
		assertEquals( 2F, makeFloat( 2F ), 0.01F );
		assertEquals( Float.MAX_VALUE, makeFloat( Float.MAX_VALUE ), 0.01F );
		assertEquals( Float.POSITIVE_INFINITY, makeFloat( Float.POSITIVE_INFINITY ), 0.01F );
	}
	
	private final float makeFloat( final float value ) {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "FloatConstImpl" ).implements_( FloatConst.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( float.class, "get" ) ).
			fconst( value ).
			freturn();
		
		FloatConst floatConst = classWriter.< FloatConst >newInstance();
		return floatConst.get();
	}
	
	public final @Test void doubleConstants() {
		assertEquals( Double.NaN, makeDouble( Double.NaN ), 0.01F );
		assertEquals( Double.NEGATIVE_INFINITY, makeDouble( Double.NEGATIVE_INFINITY ), 0.01F );		
		assertEquals( Double.MIN_VALUE, makeDouble( Double.MIN_VALUE ), 0.01D );
		assertEquals( Float.MIN_VALUE, makeDouble( Float.MIN_VALUE ), 0.01D );
		assertEquals( 0D, makeDouble( 0D ), 0.01D );
		assertEquals( 1D, makeDouble( 1D ), 0.01D );
		assertEquals( 2D, makeDouble( 2D ), 0.01D );
		assertEquals( Float.MAX_VALUE, makeDouble( Float.MAX_VALUE ), 0.01D );
		assertEquals( Double.MAX_VALUE, makeDouble( Double.MAX_VALUE ), 0.01D );
		assertEquals( Double.POSITIVE_INFINITY, makeDouble( Double.POSITIVE_INFINITY ), 0.01F );
	}
	
	private final double makeDouble( final double value ) {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "DoubleConstImpl" ).implements_( DoubleConst.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( double.class, "get" ) ).
			dconst( value ).
			dreturn();
		
		DoubleConst doubleConst = classWriter.< DoubleConst >newInstance();
		return doubleConst.get();
	}
	
	public final @Test void classConstants() {
		assertEquals( void.class, makeClass( void.class ) );
		assertEquals( boolean.class, makeClass( boolean.class ) );
		assertEquals( char.class, makeClass( char.class ) );
		assertEquals( byte.class, makeClass( byte.class ) );
		assertEquals( short.class, makeClass( short.class ) );
		assertEquals( int.class, makeClass( int.class ) );
		assertEquals( long.class, makeClass( long.class ) );
		assertEquals( float.class, makeClass( float.class ) );
		assertEquals( double.class, makeClass( double.class ) );
		assertEquals( int[].class, makeClass( int[].class ) );
		
		assertEquals( Object.class, makeClass( Object.class ) );
		assertEquals( String.class, makeClass( String.class ) );
	}
	
	private static final Class< ? > makeClass( final Class< ? > aClass ) {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().final_().class_( "ClassConstImpl" ).implements_( ObjectConst.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( Object.class, "get" ) ).
			aconst( aClass ).
			areturn();
		
		ObjectConst classConst = classWriter.< ObjectConst >newInstance();
		return (Class<?>)classConst.get();
	}
	
	public static interface BooleanConst {
		public abstract boolean get();
	}
	
	public static interface ByteConst {
		public abstract byte get();
	}
	
	public static interface CharConst {
		public abstract char get();
	}
	
	public static interface ShortConst {
		public abstract short get();
	}
	
	public static interface IntConst {
		public abstract int get();
	}
	
	public static interface LongConst {
		public abstract long get();
	}
	
	public static interface FloatConst {
		public abstract float get();
	}
	
	public static interface DoubleConst {
		public abstract double get();
	}
	
	public static interface ObjectConst {
		public abstract Object get();
	}
}
