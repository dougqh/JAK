package net.dougqh.jak.jvm.assembler.macros.api;

import static net.dougqh.jak.Jak.*;
import static net.dougqh.jak.assembler.JakAsm.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;
import net.dougqh.jak.jvm.assembler.macros.stmt;

import org.junit.Test;

public final class DoWhileTest {
	@Test
	public final void bar() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "Sum" ).implements_( Function.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( int_, "eval", int_, "x" ) ).
			ideclare( 0, "i" ).
			ideclare( 0, "sum" ).
			do_( new stmt() {
				protected void body() {
					iadd( "sum", "i" ).
					istore( "sum" ).
					iinc( "i" );
				}
			} ).while_( le( "i", "x" ) ).
			ireturn( "sum" );
		
		Function sum = classWriter.< Function >newInstance();
		assertThat( sum.eval( 4 ), is( 10 ) );
	}
	
	public static interface Function {
		public int eval( final int x );
	}
}
