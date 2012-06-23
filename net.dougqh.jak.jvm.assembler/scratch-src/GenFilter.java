import java.io.IOException;
import java.lang.reflect.Method;

import net.dougqh.jak.jvm.JvmOperationProcessor;

public class GenFilter {
	public static final void main(final String... args) throws IOException {
		SourceWriter sourceWriter = new SourceWriter();

		sourceWriter.writeln("public abstract class JvmOperationFilter implements JvmOperationProcessor {");

		for ( final Method method: JvmOperationProcessor.class.getMethods() ) {
			sourceWriter.indent( new Section() {
				@Override
				public final void write() {
					String name = method.getName();
					String formalArgs = pad(formalArguments(method));
					String passArgs = pad(passthrough(method));
					
					writeln("@Override");
					writeln("public final void %s(%s) {", name, formalArgs);
					writeln("    if ( this.shouldFilter( %s.class ) ) {", name );
					writeln("        this.hydrator.%s(%s);", name, passArgs );
					writeln("        this.filter(this.hydrator.get());");
					writeln("    } else {" );
					writeln("        this.wrapped().%s(%s);", name, passArgs );
					writeln("    }");
					writeln("}");
				}
			} );
			sourceWriter.writeln();
		}
		
		sourceWriter.writeln("}");
	}
	
	private static final boolean isNoArgs( final Method method ) {
		return ( method.getParameterTypes().length == 0 );
	}
	
	private static final String pad( final String string ) {
		if ( string.isEmpty() ) {
			return string;
		} else {
			return " " + string + " ";
		}
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
