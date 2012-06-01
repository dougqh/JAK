import java.io.File;
import java.io.IOException;

import net.dougqh.jak.jvm.assembler.JvmEnumWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;
import static net.dougqh.jak.Jak.*;


public final class Enum {
	public static final void main( final String... args ) throws IOException {
		JvmEnumWriter enumWriter = new JvmWriter().define( public_().final_().enum_( "MyEnum" ) );
		
		enumWriter.define( "FOO" );
		enumWriter.define( "BAR" );
		enumWriter.define( "BAZ" );
		
		enumWriter.writeTo( new File( "test" ) );
	}
}
