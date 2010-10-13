package net.dougqh.jak.repl;

import java.io.IOException;
import java.util.Set;

public final class OperatorCommand extends ReplCommand {
	static final OperatorCommand INSTANCE = new OperatorCommand();
	
	@Override
	final boolean matches( final String command ) {
		return ! ReplMethod.findByOperator( command ).isEmpty();
	}
	
	@Override
	final void run(
		final JakRepl repl,
		final String command,
		final String... argStrings )
		throws IOException
	{
		Set< ReplMethod > methods = ReplMethod.findByOperator( command );
		ReplMethod method = findMethod( repl, methods );
		
		if ( method != null ) {
			try {
				Object[] args = method.parseArguments( argStrings );
				method.invoke( repl.codeWriter(), args );
				repl.runProgram();
				
				return;
			} catch ( IllegalArgumentException e ) {
				repl.console().printError( "Invalid arguments" );
			}
		}
		
		repl.console().printUsage( methods );
	}
	
	private static final ReplMethod findMethod(
		final JakRepl repl,
		final Set< ReplMethod > methods )
	{
		for ( ReplMethod method : methods ) {
			if ( method.matchesStackTypes( repl ) ) {
				return method;
			}
		}
		return null;
	}
}
