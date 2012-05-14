package net.dougqh.jak.jvm.assembler.macros.api;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static net.dougqh.jak.Jak.*;
import static net.dougqh.jak.assembler.JakAsm.*;
import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;
import net.dougqh.jak.jvm.assembler.macros.stmt;

import org.junit.Test;

public final class WhileTest {
	@Test
	public final void whileCond() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "Sum" ).implements_( Function.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( int_, "eval", int_, "limit" ) ).
			ideclare( 0, "sum" ).
			ideclare( 1, "i" ).
			while_( le( "i", "limit" ), new stmt() {
				protected void body() {
					iadd( "sum", "i" ).
					istore( "sum" ).
					iinc( "i" );
				}
			} ).
			ireturn( "sum" );
		
		Function sum = classWriter.< Function >newInstance();
		assertThat( sum.eval( 4 ), is( 10 ) );
	}
	
	public static interface Function {
		public int eval( final int limit );
	}
}
