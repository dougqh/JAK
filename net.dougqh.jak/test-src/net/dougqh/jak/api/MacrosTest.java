package net.dougqh.jak.api;

import static net.dougqh.jak.JavaAssembler.*;
import static org.junit.Assert.*;
import net.dougqh.jak.JavaClassWriter;

import org.junit.Test;

public final class MacrosTest {
	public final @Test void not() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "Not" ).implements_( BooleanOperation.class ) );
	
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( boolean.class, "calculate", boolean.class ) ).
			iload_1().
			inot().
			ireturn();
		
		BooleanOperation not = classWriter.< BooleanOperation >newInstance();
		
		assertTrue( not.calculate( false ) );
		assertFalse( not.calculate( true ) );
	}
	
	public final @Test void box() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "UnboxerImpl" ).implements_( Unboxer.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( boolean.class, "unbox", Boolean.class ) ).
			aload_1().
			unbox( Boolean.class ).
			ireturn();

		classWriter.define(
			public_().final_().method( byte.class, "unbox", Byte.class ) ).
			aload_1().
			unbox( Byte.class ).
			ireturn();
		
		classWriter.define(
			public_().final_().method( char.class, "unbox", Character.class ) ).
			aload_1().
			unbox( Character.class ).
			ireturn();
		
		classWriter.define(
			public_().final_().method( short.class, "unbox", Short.class ) ).
			aload_1().
			unbox( Short.class ).
			ireturn();
	
		classWriter.define(
			public_().final_().method( int.class, "unbox", Integer.class ) ).
			aload_1().
			unbox( Integer.class ).
			ireturn();
		
		classWriter.define(
			public_().final_().method( long.class, "unbox", Long.class ) ).
			aload_1().
			unbox( Long.class ).
			lreturn();
		
		classWriter.define(
			public_().final_().method( float.class, "unbox", Float.class ) ).
			aload_1().
			unbox( Float.class ).
			freturn();
		
		classWriter.define(
			public_().final_().method( double.class, "unbox", Double.class ) ).
			aload_1().
			unbox( Double.class ).
			dreturn();
		
		Unboxer unboxer = classWriter.< Unboxer >newInstance();
		assertTrue( unboxer.unbox( Boolean.TRUE ) );
		assertFalse( unboxer.unbox( Boolean.FALSE ) );
		assertEquals( (byte)0, unboxer.unbox( Byte.valueOf( (byte)0 ) ) );
		assertEquals( 'a', unboxer.unbox( Character.valueOf( 'a' ) ) );
		assertEquals( (short)0, unboxer.unbox( Short.valueOf( (short)0 ) ) );
		assertEquals( 0, unboxer.unbox( Integer.valueOf( 0 ) ) );
		assertEquals( 0L, unboxer.unbox( Long.valueOf( 0L ) ) );
		assertEquals( 0F, unboxer.unbox( Float.valueOf( 0F ) ), 0.01F );
		assertEquals( 0D, unboxer.unbox( Double.valueOf( 0D ) ), 0.01D );
	}
	
	public final @Test void unbox() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "BoxerImpl" ).implements_( Boxer.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( Boolean.class, "box", boolean.class ) ).
			iload_1().
			box( boolean.class ).
			areturn();
		
		classWriter.define(
			public_().final_().method( Byte.class, "box", byte.class ) ).
			iload_1().
			box( byte.class ).
			areturn();
		
		classWriter.define(
			public_().final_().method( Character.class, "box", char.class ) ).
			iload_1().
			box( char.class ).
			areturn();

		classWriter.define(
			public_().final_().method( Short.class, "box", short.class ) ).
			iload_1().
			box( short.class ).
			areturn();
		
		classWriter.define(
			public_().final_().method( Integer.class, "box", int.class ) ).
			iload_1().
			box( int.class ).
			areturn();
		
		classWriter.define(
			public_().final_().method( Long.class, "box", long.class ) ).
			lload_1().
			box( long.class ).
			areturn();

		classWriter.define(
			public_().final_().method( Float.class, "box", float.class ) ).
			fload_1().
			box( float.class ).
			areturn();
		
		classWriter.define(
			public_().final_().method( Double.class, "box", double.class ) ).
			dload_1().
			box( double.class ).
			areturn();
		
		Boxer boxer = classWriter.< Boxer >newInstance();
		assertEquals( Boolean.TRUE, boxer.box( true ) );
		assertEquals( Boolean.FALSE, boxer.box( false ) );
		assertEquals( Byte.valueOf( (byte)0 ), boxer.box( (byte)0 ) );
		assertEquals( Character.valueOf( 'a' ), boxer.box( 'a' ) );
		assertEquals( Short.valueOf( (short)0 ), boxer.box( (short)0 ) );
		assertEquals( Integer.valueOf( 0 ), boxer.box( 0 ) );
		assertEquals( Long.valueOf( 0L ), boxer.box( 0L ) );
		assertEquals( Float.valueOf( 0F ), boxer.box( 0F ) );
		assertEquals( Double.valueOf( 0D ), boxer.box( 0D ) );
	}
	
	public static interface BooleanOperation {
		public abstract boolean calculate( final boolean value );
	}
	
	public static interface Unboxer {
		public abstract boolean unbox( final Boolean value );
		
		public abstract byte unbox( final Byte value );
		
		public abstract char unbox( final Character value );
		
		public abstract short unbox( final Short value );
		
		public abstract int unbox( final Integer value );
		
		public abstract long unbox( final Long value );
		
		public abstract float unbox( final Float value );
		
		public abstract double unbox( final Double value );
	}
	
	public static interface Boxer {
		public abstract Boolean box( final boolean value );
		
		public abstract Byte box( final byte value );
		
		public abstract Character box( final char value );
		
		public abstract Short box( final short value );
		
		public abstract Integer box( final int value );
		
		public abstract Long box( final long value );
		
		public abstract Float box( final float value );
		
		public abstract Double box( final double value );
	}
}
