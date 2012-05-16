package net.dougqh.jak.jvm.assembler.macros.api;

import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;
import net.dougqh.jak.jvm.assembler.macros.stmt;

import org.junit.Test;

import static net.dougqh.jak.Jak.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public final class Nesting {
	@Test
	public final void loops() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "Sum" ).implements_( MatrixProcessor.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( int_, "eval", int[][].class, "matrix" ) ).
			ideclare( 0, "sum" ).
			array_for( int[].class, "row", "matrix", new stmt() {
				protected void body() {
					array_for( int_, "x", "row", new stmt() {
						@Override
						protected void body() {
							iadd( "sum", "x" ).
							istore( "sum" );
						}
					} );
				}
			} ).
			ireturn( "sum" );
		
		MatrixProcessor sum = classWriter.< MatrixProcessor >newInstance();
		
		int[][] matrix = {
			{ 0, 1, 2, 3, 4 },
			{ 5, 6, 7, 8 },
			{ 9, 10, 11 },
			{ 12, 13 },
			{ 14 }
		};
		
		assertThat( sum.eval( matrix ), is( 105 ) );
	}
	
	public interface MatrixProcessor {
		public int eval( final int[][] matrix );
	}
}
