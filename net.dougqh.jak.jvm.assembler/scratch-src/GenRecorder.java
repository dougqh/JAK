import java.io.IOException;
import java.lang.reflect.Method;

import net.dougqh.jak.annotations.JvmOp;
import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;

public class GenRecorder {
	public static final void main(final String... args) throws IOException {
		for ( Method method: JvmCoreCodeWriter.class.getMethods() ) {
			JvmOp jvmOp = method.getAnnotation(JvmOp.class);
			Class<?>[] paramTypes = method.getParameterTypes();
			
			System.out.printf( "\tpublic final JvmCoreCodeWriter %s( ", method.getName() );
			for ( int i = 0; i < paramTypes.length; ++i ) {
				if ( i != 0 ) {
					System.out.print( ", " );
				}
				System.out.printf( "%s arg%d", paramTypes[i].getSimpleName(), i );
			}
			System.out.println( " ) {" );
			
			if ( paramTypes.length != 0 ) {
				System.out.printf("\tif( this.trap( %s.class ) {%n", method.getName() );
				System.out.printf( "\t\tthis.exec( %s.instance() );%n", method.getName() );
				System.out.println( "} else {" );
				System.out.printf( "\t\tthis.wrapped().%s();%n", method.getName() );
				System.out.println( "}" );
				System.out.println( "return this;" );
			} else {
				StringBuilder argsBuilder = new StringBuilder();
				for ( int i = 0; i < paramTypes.length; ++i ) {
					if ( i != 0 ) {
						argsBuilder.append( ", " );
					}
					argsBuilder.append( String.format( "arg%d", i ) ); 
				}
				
				
				System.out.printf("\tif( this.handle( %s.class ) ) (  {%n", method.getName() );
				System.out.printf( "\t\tthis.wrapped().%s();%n", method.getName() );
				System.out.println( "} else {" );
				System.out.printf( "\t\tthis.record( %s.instance() );%n", method.getName() );
				System.out.println( "}" );
				System.out.println( "return this;" );
			}
			
<<<<<<< HEAD
			//System.out.println( )
=======
			System.out.println( )
>>>>>>> enums
			System.out.println( "\t}" );
			
			System.out.println( "}" );
			System.out.println();
		}
	}
	
<<<<<<< HEAD
	//private static final boolean 
=======
	private static final boolean 
>>>>>>> enums
}
