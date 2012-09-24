import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.dougqh.jak.jvm.operations.JvmOperation;


public class DecoderSwitch {
	public static final void main(final String... args) {
		SourceWriter sourceWriter = new SourceWriter();
		
		for ( Field field: JvmOperation.class.getFields() ) {
			if ( ! isOpConstant( field ) ) {
				continue;
			}
			
			sourceWriter.writeln( "case %s:", field.getName() );
			sourceWriter.writeln( "processor.%s();", field.getName().toLowerCase() );
			sourceWriter.writeln( "break;" );
			sourceWriter.writeln();
		}
	}
	
	private static final boolean isOpConstant( final Field field ) {
		return Modifier.isStatic( field.getModifiers() ) &&
			field.getType().equals( byte.class );
	}
}
