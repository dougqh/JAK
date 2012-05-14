package net.dougqh.jak.jvm.assembler.macros;

import static net.dougqh.jak.Jak.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;

import org.junit.Test;

public final class SynchronizedTest {
	@Test
	public final void sync() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "FunctionImpl" ).implements_( Function.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( int.class, "eval" ) ).
			ideclare( 0, "x" ).
			synchronized_( "this", new stmt() {
				protected void body() {
					iinc( "x" );
				}
			} ).
			ireturn( "x" );
		
		Function func = classWriter.< Function >newInstance();
		assertThat( func.eval(), is( 1 ) );
	}
	
	@Test
	public final void sync_with_return() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "FunctionImpl" ).implements_( Function.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( int.class, "eval" ) ).ideclare( 0, "x" ).
			synchronized_( "this", new stmt() {
				protected void body() {
					ireturn( 1 );
				}
			} );
		
		Function func = classWriter.< Function >newInstance();
		assertThat( func.eval(), is( 1 ) );
	}
	
	public static interface Function {
		public int eval();
	}
}
