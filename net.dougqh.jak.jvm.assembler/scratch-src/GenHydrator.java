import java.io.IOException;
import java.lang.reflect.Method;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public class GenHydrator {
	public static final void main(final String... args) throws IOException {
		SourceWriter sourceWriter = new SourceWriter();

		sourceWriter.writeln("public abstract class JvmOperationHydrator implements JvmOperationProcessor {");

		for ( final Method method: JvmOperationProcessor.class.getMethods() ) {
			sourceWriter.indent( new Section() {
				@Override
				public final void write() {
					if ( isNoArgs(method) ) {
						String name = method.getName();
						
						writeln("@Override");
						writeln("public final void %s() {", name);
						writeln("    this.add( %s.instance() );", name);
						writeln("}");
					} else {
						String name = method.getName();
						String formalArgs = formalArguments(method);
						String passArgs = passthrough(method);
						
						writeln("@Override");
						writeln("public final void %s( %s ) {", name, formalArgs);
						writeln("    this.add( new %s( %s ) );", name, passArgs);
						writeln("}");
					}
				}
			} );
			sourceWriter.writeln();
		}
		
		sourceWriter.writeln("}");
	}
	
	private static final boolean isNoArgs( final Method method ) {
		return ( method.getParameterTypes().length == 0 );
	}
	
	private static final String formalArguments( final Method method ) {
		Class<?>[] paramTypes = method.getParameterTypes();
		
		StringBuilder argsBuilder = new StringBuilder();
		for ( int i = 0; i < paramTypes.length; ++i ) {
			if ( i != 0 ) {
				argsBuilder.append( ", " );
			}
			argsBuilder.append( String.format( "final %s %s", name(paramTypes[i]), varName(paramTypes[i]) ) );
		}
		return argsBuilder.toString();
	}
	
	private static final String passthrough( final Method method ) {
		Class<?>[] paramTypes = method.getParameterTypes();
		
		StringBuilder argsBuilder = new StringBuilder();
		for ( int i = 0; i < paramTypes.length; ++i ) {
			if ( i != 0 ) {
				argsBuilder.append( ", " );
			}
			argsBuilder.append( String.format( "%s", varName(paramTypes[i]) ) );
		}
		return argsBuilder.toString();
	}
	
	private static final String name( final Class<?> type ) {
		return type.getSimpleName();
	}
	
	private static final String varName( final Class<?> type ) {
		return type.getSimpleName().toLowerCase();
	}
}
