package net.dougqh.jak.jvm.assembler.api;

import static net.dougqh.jak.Jak.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmCodeWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;

import org.junit.Test;

public final class ArithmeticOpsTest {
	public static final ClassLoader CLASS_LOADER = ArithmeticOpsTest.class.getClassLoader();
	
	private static final double DELTA = 1e-8;
	
	public final @Test void intOps() {
		TestClassLoader classLoader = new TestClassLoader( CLASS_LOADER, "IntOps" ) {
			@Override
			protected final byte[] generateClass() throws IOException {
				JvmClassWriter writer = new JvmWriter().define( public_().final_().class_( "IntOps" ) );
				
				binaryMethod( writer, "add" ).
					iload_0().
					iload_1().
					iadd().
					ireturn();
				
				binaryMethod( writer, "sub" ).
					iload_0().
					iload_1().
					isub().
					ireturn();
				
				binaryMethod( writer, "mul" ).
					iload( 0 ).
					iload( 1 ).
					imul().
					ireturn();
				
				binaryMethod( writer, "div" ).
					iload_0().
					iload_1().
					idiv().
					ireturn();
				
				binaryMethod( writer, "rem" ).
					iload_0().
					iload( 1 ).
					irem().
					ireturn();
				
				binaryMethod( writer, "and" ).
					iload( 0 ).
					iload( 1 ).
					iand().
					ireturn();
				
				binaryMethod( writer, "or" ).
					iload( 0 ).
					iload( 1 ).
					ior().
					ireturn();
				
				binaryMethod( writer, "xor" ).
					iload( 0 ).
					iload( 1 ).
					ixor().
					ireturn();
				
				binaryMethod( writer, "left" ).
					iload( 0 ).
					iload( 1 ).
					ishl().
					ireturn();
				
				binaryMethod( writer, "right" ).
					iload( 0 ).
					iload( 1 ).
					ishr().
					ireturn();
				
				binaryMethod( writer, "uright" ).
					iload( 0 ).
					iload( 1 ).
					iushr().
					ireturn();
				
				unaryMethod( writer, "neg" ).
					iload( 0 ).
					ineg().
					ireturn();
				
				unaryMethod( writer, "inc" ).
					iinc( 0 ).
					iload( 0 ).
					ireturn();
				
				constMethod( writer, "negtwo" ).
					bipush( (byte)-2 ).
					ireturn();
				
				constMethod( writer, "negone" ).
					iconst_m1().
					ireturn();
				
				constMethod( writer, "zero" ).
					iconst_0().
					ireturn();
				
				constMethod( writer, "one" ).
					iconst_1().
					ireturn();
				
				constMethod( writer, "two" ).
					iconst_2().
					ireturn();
				
				constMethod( writer, "three" ).
					iconst_3().
					ireturn();
				
				constMethod( writer, "four" ).
					iconst_4().
					ireturn();
				
				constMethod( writer, "five" ).
					iconst_5().
					ireturn();
				
				constMethod( writer, "six" ).
					bipush( (byte)6 ).
					ireturn();
				
				constMethod( writer, "thousand" ).
					sipush( (short)1000 ).
					ireturn();
				
				return writer.getBytes();
			}
			
			private /* static */ final JvmCodeWriter constMethod(
				final JvmClassWriter classWriter,
				final String name )
			{
				return classWriter.define(
					public_().static_().final_().method( int.class, name ) );
			}
			
			private /* static */ final JvmCodeWriter unaryMethod(
				final JvmClassWriter classWriter,
				final String name )
			{
				return classWriter.define(
					public_().static_().final_().
						method( int.class, name ).args( int.class ) );
			}
		
			private /* static */ final JvmCodeWriter binaryMethod(
				final JvmClassWriter classWriter,
				final String name )
			{
				return classWriter.define(
					public_().static_().final_().
						method( int.class, name ).args( int.class, int.class ) );
			}
		};
		
		Class< ? > intOps = classLoader.loadClass();
		
		assertEquals( 9 + 10, invokeInt( intOps, "add", 9, 10 ) );
		assertEquals( 100 - 99, invokeInt( intOps, "sub", 100, 99 ) );
		assertEquals( 80 * 27, invokeInt( intOps, "mul", 80, 27 ) );
		assertEquals( 144 / 12, invokeInt( intOps, "div", 144, 12 ) );
		assertEquals( 90 % 33, invokeInt( intOps, "rem", 90, 33 ) );
		
		assertEquals( 0xCAFEBABE, invokeInt( intOps, "and", 0xCAFEFFFF, 0xFFFFBABE ) );
		assertEquals( 0xCAFEBABE, invokeInt( intOps, "or", 0xCAFE0000, 0xC0F0BABE ) );
		assertEquals( 0xCAFEBABE, invokeInt( intOps, "xor", 0xCAFE0000, 0x0000BABE ) );
		
		assertEquals( 0xF0000000, invokeInt( intOps, "left", 0x0000000F, 28 ) );
		assertEquals( 0x00000007, invokeInt( intOps, "right", 0x70000000, 28 ) );
		assertEquals( 0x0000000F, invokeInt( intOps, "uright", 0xF0000000, 28 ) );
		
		assertEquals( -1000, invokeInt( intOps, "neg", 1000 ) );
		assertEquals( 89 + 1, invokeInt( intOps, "inc", 89 ) );

		assertEquals( -2, invokeInt( intOps, "negtwo" ) );
		assertEquals( -1, invokeInt( intOps, "negone" ) );
		assertEquals( 0, invokeInt( intOps, "zero" ) );
		assertEquals( 1, invokeInt( intOps, "one" ) );
		assertEquals( 2, invokeInt( intOps, "two" ) );
		assertEquals( 3, invokeInt( intOps, "three" ) );
		assertEquals( 4, invokeInt( intOps, "four" ) );
		assertEquals( 5, invokeInt( intOps, "five" ) );
		assertEquals( 6, invokeInt( intOps, "six" ) );
		assertEquals( 1000, invokeInt( intOps, "thousand" ) );
	}
	
	public final @Test void floatOps() {
		TestClassLoader classLoader = new TestClassLoader( CLASS_LOADER, "FloatOps" ) {
			@Override
			protected final byte[] generateClass() throws IOException {
				JvmClassWriter writer = new JvmWriter().define( public_().final_().class_( this.className ) );

				binaryMethod( writer, "add" ).
					fload_0().
					fload_1().
					fadd().
					freturn();
				
				binaryMethod( writer, "sub" ).
					fload( 0 ).
					fload( 1 ).
					fsub().
					freturn();
				
				binaryMethod( writer, "mul" ).
					fload( 0 ).
					fload_1().
					fmul().
					freturn();
				
				binaryMethod( writer, "div" ).
					fload_0().
					fload_1().
					fdiv().
					freturn();
				
				binaryMethod( writer, "rem" ).
					fload_0().
					fload_1().
					frem().
					freturn();
				
				unaryMethod( writer, "neg" ).
					fload_0().
					fneg().
					freturn();
				
				constMethod( writer, "zero" ).
					fconst_0().
					freturn();
				
				constMethod( writer, "one" ).
					fconst_1().
					freturn();
				
				constMethod( writer, "two" ).
					fconst_2().
					freturn();
				
				return writer.getBytes();
			}
			
			private /* static */ final JvmCodeWriter constMethod(
				final JvmClassWriter classWriter,
				final String name )
			{
				return classWriter.define(
					public_().static_().final_().method( float.class, name ) );			}
			
			private /* static */ final JvmCodeWriter unaryMethod(
				final JvmClassWriter classWriter,
				final String name )
			{
				return classWriter.define(
					public_().static_().final_().
						method( float.class, name ).args( float.class ) );
			}
		
			private /* static */ final JvmCodeWriter binaryMethod(
				final JvmClassWriter classWriter,
				final String name )
			{
				return classWriter.define(
					public_().static_().final_().
						method( float.class, name ).args( float.class, float.class ) );
			}
		};
		
		Class< ? > floatOps = classLoader.loadClass();
		assertEquals( 3f + 2f, invokeFloat( floatOps, "add", 3f, 2f ), DELTA );
		assertEquals( 3f - 2f, invokeFloat( floatOps, "sub", 3f, 2f ), DELTA );
		assertEquals( 3f * 2f, invokeFloat( floatOps, "mul", 3f, 2f ), DELTA );
		assertEquals( 3f / 2f, invokeFloat( floatOps, "div", 3f, 2f ), DELTA );
		assertEquals( 3f % 2f, invokeFloat( floatOps, "rem", 3f, 2f ), DELTA );
		
		assertEquals( -3f, invokeFloat( floatOps, "neg", 3f ), DELTA );
		
		assertEquals( 0f, invokeFloat( floatOps, "zero" ), DELTA );
		assertEquals( 1f, invokeFloat( floatOps, "one" ), DELTA );
		assertEquals( 2f, invokeFloat( floatOps, "two" ), DELTA );
	}
	
	public final @Test void longOps() {
		TestClassLoader classLoader = new TestClassLoader( CLASS_LOADER, "LongOps" ) {			
			@Override
			protected final byte[] generateClass() throws IOException {
				JvmClassWriter writer = new JvmWriter().define( public_().final_().class_( this.className ) );

				binaryMethod( writer, "add" ).
					lload_0().
					lload_2().
					ladd().
					lreturn();
				
				binaryMethod( writer, "sub" ).
					lload( 0 ).
					lload_2().
					lsub().
					lreturn();
				
				binaryMethod( writer, "mul" ).
					lload( 0 ).
					lload( 2 ).
					lmul().
					lreturn();
				
				binaryMethod( writer, "div" ).
					lload( 0 ).
					lload( 2 ).
					ldiv().
					lreturn();
				
				binaryMethod( writer, "rem" ).
					lload( 0 ).
					lload( 2 ).
					lrem().
					lreturn();
				
				binaryMethod( writer, "and" ).
					lload( 0 ).
					lload_2().
					land().
					lreturn();
				
				binaryMethod( writer, "or" ).
					lload_0().
					lload( 2 ).
					lor().
					lreturn();
				
				binaryMethod( writer, "xor" ).
					lload( 0 ).					
					lload( 2 ).
					lxor().
					lreturn();
				
				shiftMethod( writer, "left" ).
					lload( 0 ).
					iload( 2 ).
					lshl().
					lreturn();
				
				shiftMethod( writer, "right" ).
					lload( 0 ).
					iload( 2 ).
					lshr().
					lreturn();
				
				shiftMethod( writer, "uright" ).
					lload( 0 ).
					iload( 2 ).
					lushr().
					lreturn();
				
				unaryMethod( writer, "neg" ).
					lload( 0 ).
					lneg().
					lreturn();
				
				constMethod( writer, "zero" ).
					lconst_0().
					lreturn();
				
				constMethod( writer, "one" ).
					lconst_1().
					lreturn();
				
				return writer.getBytes();
			}
			
			private /* static */ final JvmCodeWriter constMethod(
				final JvmClassWriter classWriter,
				final String name )
			{
				return classWriter.define(
					public_().static_().final_().method( long.class, name ) );
			}
			
			private /* static */ final JvmCodeWriter unaryMethod(
				final JvmClassWriter classWriter,
				final String name )
			{
				return classWriter.define(
					public_().static_().final_().
						method( long.class, name ).args( long.class ) );
			}
		
			private /* static */ final JvmCodeWriter binaryMethod(
				final JvmClassWriter classWriter,
				final String name )
			{
				return classWriter.define(
					public_().static_().final_().
						method( long.class, name ).args( long.class, long.class ) );
			}
			
			private /* static */ final JvmCodeWriter shiftMethod(
				final JvmClassWriter classWriter,
				final String name )
			{
				return classWriter.define(
					public_().static_().final_().
						method( long.class, name ).args( long.class, int.class ) );
			}		
		};
		
		Class< ? > longOps = classLoader.loadClass();
		assertEquals( 100000L + 99999L, invokeLong( longOps, "add", 100000L, 99999L ) );
		assertEquals( 100000L - 99999L, invokeLong( longOps, "sub", 100000L, 99999L ) );
		assertEquals( 100000L * 99999L, invokeLong( longOps, "mul", 100000L, 99999L ) );
		assertEquals( 100000L / 99999L, invokeLong( longOps, "div", 100000L, 99999L ) );
		assertEquals( 100000L % 99999L, invokeLong( longOps, "rem", 100000L, 99999L ) );
		
		assertEquals(
			0x0F000000000F0L,
			invokeLong( longOps, "and", 0x0F0F0F0F0F0F0L, 0x0F000000000F0L ) );
		assertEquals(
			0xFFFFFFFFFFFFFL,
			invokeLong( longOps, "or", 0x0F0F0F0F0F0F0L, 0xF0F0F0F0F0F0FL ) ); 
		assertEquals(			
			0x0000000000000L,
			invokeLong( longOps, "xor", 0xFFFFFFFFFFFFFL, 0xFFFFFFFFFFFFFL ) ); 

		assertEquals( 100000L << 10, invokeShift( longOps, "left", 100000L, 10 ) );
		assertEquals( 100000L >> 10, invokeShift( longOps, "right", 100000L, 10 ) );
		assertEquals( 100000L >>> 10, invokeShift( longOps, "uright", 100000L, 10 ) );
		
		assertEquals( Long.MAX_VALUE, invokeLong( longOps, "neg", Long.MIN_VALUE + 1 ) );
		
		assertEquals( 0L, invokeLong( longOps, "zero" ) );
		assertEquals( 1L, invokeLong( longOps, "one" ) );
	}
	
	public final @Test void doubleOps() {
		TestClassLoader classLoader = new TestClassLoader( CLASS_LOADER, "DoubleOps" ) {
			@Override
			protected final byte[] generateClass() throws IOException {
				JvmClassWriter writer = new JvmWriter().define( public_().final_().class_( this.className ) );
				
				binaryMethod( writer, "add" ).
					dload_0().
					dload_2().
					dadd().
					dreturn();

				binaryMethod( writer, "sub" ).
					dload( 0 ).
					dload_2().
					dsub().
					dreturn();
				
				binaryMethod( writer, "mul" ).
					dload_0().
					dload( 2 ).
					dmul().
					dreturn();
				
				binaryMethod( writer, "div" ).
					dload( 0 ).
					dload( 2 ).
					ddiv().
					dreturn();
				
				binaryMethod( writer, "rem" ).
					dload( 0 ).
					dload( 2 ).
					drem().
					dreturn();
				
				unaryMethod( writer, "neg" ).
					dload_0().
					dneg().
					dreturn();
				
				constMethod( writer, "zero" ).
					dconst_0().
					dreturn();
				
				constMethod( writer, "one" ).
					dconst_1().
					dreturn();
				
				return writer.getBytes();
			}
			
			private /* static */ final JvmCodeWriter constMethod(
				final JvmClassWriter classWriter,
				final String name )
			{
				return classWriter.define(
					public_().static_().final_().method( double.class, name ) );
			}
			
			private /* static */ final JvmCodeWriter unaryMethod(
				final JvmClassWriter classWriter,
				final String name )
			{
				return classWriter.define(
					public_().static_().final_().
						method( double.class, name ).args( double.class ) );
			}
		
			private /* static */ final JvmCodeWriter binaryMethod(
				final JvmClassWriter classWriter,
				final String name )
			{
				return classWriter.define(
					public_().static_().final_().
						method( double.class, name ).args( double.class, double.class ) );
			}
		};
		
		Class< ? > doubleOps = classLoader.loadClass();
		assertEquals( 100D + 0.001D, invokeDouble( doubleOps, "add", 100D, 0.001D ), DELTA );
		assertEquals( 100D - 0.001D, invokeDouble( doubleOps, "sub", 100D, 0.001D ), DELTA );
		assertEquals( 100D * 0.001D, invokeDouble( doubleOps, "mul", 100D, 0.001D ), DELTA );
		assertEquals( 100D / 0.001D, invokeDouble( doubleOps, "div", 100D, 0.001D ), DELTA );
		assertEquals( 100D % 0.001D, invokeDouble( doubleOps, "rem", 100D, 0.001D ), DELTA );
		
		assertEquals( -100D, invokeDouble( doubleOps, "neg", 100D ), DELTA );
		assertEquals( 0D, invokeDouble( doubleOps, "zero" ), DELTA );
		assertEquals( 1D, invokeDouble( doubleOps, "one" ), DELTA );
	}

	private static final int invokeInt(
		final Class< ? > aClass,
		final String methodName,
		final Integer... args )
	{		
		Class< ? >[] argTypes = new Class[ args.length ];
		Arrays.fill( argTypes, int.class );
		
		return (Integer)invoke(
			getMethod( aClass, methodName, argTypes ),
			(Object[])args );
	}
	
	private static final float invokeFloat(
		final Class< ? > aClass,
		final String methodName,
		final Float... args )
	{		
		Class< ? >[] argTypes = new Class[ args.length ];
		Arrays.fill( argTypes, float.class );
		
		return (Float)invoke(
			getMethod( aClass, methodName, argTypes ),
			(Object[])args );
	}
	
	private static final long invokeLong(
		final Class< ? > aClass,
		final String methodName,
		final Long... args )
	{
		Class< ? >[] argTypes = new Class[ args.length ];
		Arrays.fill( argTypes, long.class );
		
		return (Long)invoke(
			getMethod( aClass, methodName, argTypes ),
			(Object[])args );
	}
	
	private static final double invokeDouble(
		final Class< ? > aClass,
		final String methodName,
		final Double... args )
	{
		Class< ? >[] argTypes = new Class[ args.length ];
		Arrays.fill( argTypes, double.class );
		
		return (Double)invoke(
			getMethod( aClass, methodName, argTypes ),
			(Object[])args );
	}
	
	private static final long invokeShift(
		final Class< ? > aClass,
		final String methodName,
		final long arg0,
		final int arg1 )
	{
		return (Long)invoke(
			getMethod( aClass, methodName, long.class, int.class ),
			arg0,
			arg1 );
	}
	
	private static final Method getMethod(
		final Class< ? > aClass,
		final String name,
		final Class< ? >... argTypes )
	{
		try {
			return aClass.getDeclaredMethod( name, argTypes );
		} catch ( NoSuchMethodException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	private static final Object invoke(
		final Method method,
		final Object... args )
	{
		try {
			return method.invoke( null, args );
		} catch ( IllegalAccessException e ) {
			throw new IllegalStateException( e );
		} catch ( InvocationTargetException e ) {
			throw new IllegalStateException( e );
		}
	}
}
