import static net.dougqh.jak.JavaAssembler.*;

import java.io.File;

import net.dougqh.jak.JavaAnnotationWriter;

public final class Gen {
	public static final void main( final String[] args ) throws Exception {
		JavaAnnotationWriter annotationWriter = define( public_().$interface( "Annotation" ) );
		
		annotationWriter.define( field( String.class, "value" ) );

		annotationWriter.writeTo( new File( args[ 0 ] ) );
	}
}
