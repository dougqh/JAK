import static net.dougqh.jak.Jak.*;

import java.io.File;
import java.io.IOException;

import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;
import net.dougqh.jak.jvm.assembler.macros.SynchronizedTest.Function;
import net.dougqh.jak.jvm.assembler.macros.stmt;


public class Sync {
	public static final void main( final String... args ) throws IOException {
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
	
		classWriter.writeTo( new File( "test" ) );
	}
}
