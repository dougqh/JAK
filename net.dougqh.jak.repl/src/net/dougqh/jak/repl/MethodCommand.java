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
	final boolean runProgramAfterCommand() {
		return true;
	}
	
	@Override
	final boolean run(
		final JakRepl repl,
		final String methodName,
		final String[] argStrings )
		throws IOException
	{
		Set< ReplMethod > methods = ReplMethod.findByName( methodName );
		if ( methods.isEmpty() ) {
			repl.console().printError( "Unknown command: " + methodName );
			if ( argStrings.length == 0 ) {
				repl.console().complete( methodName );
			}
			return false;
		} else {
			//DQH - 10-11-2010 - Pretty ugly, loop over matching methods 
			//until an implementations that has matching arguments is found.
			//If no match is found, then print out usage.
			for ( ReplMethod method : methods ) {
				try {
					Object[] args = method.parseArguments( repl, argStrings );
					method.invoke( repl.codeWriter(), args );
					return true;
				} catch ( IllegalArgumentException e ) {
				}				
			}
			
			repl.console().printError( "Invalid arguments" );
			repl.console().printUsage( methods );
			return false;
		}		
	}
}
