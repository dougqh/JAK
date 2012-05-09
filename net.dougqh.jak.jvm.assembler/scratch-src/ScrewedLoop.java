import static net.dougqh.jak.Jak.*;
import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;
import net.dougqh.jak.jvm.assembler.macros.stmt;

public final class ScrewedLoop {
	public static final void main( final String... args ) {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "Sum" ).implements_( ArrayProcessor.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define( public_().method( int_, "process" ).arg( int[].class, "array" ) ).
			iconst_0().
			array_for( int_, "x", "array", new stmt() {
				protected void body() {
					iload( "x" );
					iadd();
				}
			} ).
			ireturn();
	
		ArrayProcessor sum = classWriter.<ArrayProcessor>newInstance();
		System.out.println( sum.process( 1, 2, 3, 4 ) );
	}
	
	public static interface ArrayProcessor {
		public abstract int process( final int... array );
	}
}
