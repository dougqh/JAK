package net.dougqh.jak.jvm.assembler.macros;

import static net.dougqh.jak.Jak.*;
import static org.junit.Assert.*;
import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

public final class ArrayForTest {
	@Test
	public final void int_() {
		JvmWriter jvmWriter = new JvmWriter();
		
		JvmClassWriter classWriter = jvmWriter.define(
			public_().final_().class_( "IntArrayProcessor" ).extends_( ArrayProcessor.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().final_().method( int_, "process", int[].class ) ).
			ideclare( "sum" ).
			istore( 0, "sum" ).
			array_for( int_, "x", "arg$0", new stmt() {
				@Override
				protected final void body() {
					iadd( "sum", "x" ).
					istore( "sum" );
				}
			} ).
			ireturn( "sum" );
		
		ArrayProcessor intArrayProcessor = classWriter.<ArrayProcessor>newInstance();
		assertThat( intArrayProcessor.process( new int[]{1, 2, 3, 4} ), is( 10 ) );
	}
	
	public static abstract class ArrayProcessor {
		public boolean process( final boolean[] array ) {
			return false;
		}
		
		public byte process( final byte[] array ) {
			return (byte)0;
		}
		
		public char process( final char[] array ) {
			return '\0';
		}
		
		public short process( final short[] array ) {
			return 0;
		}
		
		public int process( final int[] array ) {
			return 0;
		}
		
		public long process( final long[] array ) {
			return 0L;
		}
		
		public float process( final float[] array ) {
			return 0F;
		}
		
		public double process( final double[] array ) {
			return 0D;
		}
		
		public String process( final String[] array ) {
			return "";
		}
	}
}
