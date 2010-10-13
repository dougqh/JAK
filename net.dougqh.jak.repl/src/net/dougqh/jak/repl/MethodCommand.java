package net.dougqh.jak.repl;

import java.io.IOException;
import java.util.Set;

final class MethodCommand extends ReplCommand {
	static final MethodCommand INSTANCE = new MethodCommand();
	
	private MethodCommand() {}
	
	@Override
	final boolean matches( final String command ) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	final void run(
		final JakRepl repl,
		final String methodName,
		final String[] argStrings,
		final boolean isSolitary )
		throws IOException
	{
		Set< ReplMethod > methods = ReplMethod.findByName( methodName );
		if ( methods.isEmpty() ) {
			repl.console().printError( "Unknown command: " + methodName );
			if ( argStrings.length == 0 ) {
				repl.console().complete( methodName );
			}
		} else {
			//DQH - 10-11-2010 - Pretty ugly, loop over matching methods 
			//until an implementations that has matching arguments is found.
			//If no match is found, then print out usage.
			boolean foundMatch = false;
			for ( ReplMethod method : methods ) {
				try {
					Object[] args = method.parseArguments( argStrings );
					method.invoke( repl.codeWriter(), args );
					repl.runProgram();

					foundMatch = true;
					break;
				} catch ( IllegalArgumentException e ) {
				}				
			}
			if ( ! foundMatch ) {
				repl.console().printError( "Invalid arguments" );
				repl.console().printUsage( methods );
			}
		}		
	}
}
