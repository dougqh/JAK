package net.dougqh.jak.repl;

import java.io.IOException;
import java.util.ArrayList;

final class ExpressionCommand extends ReplCommand {
	static final ExpressionCommand INSTANCE = new ExpressionCommand();
	
	private ExpressionCommand() {}
	
	@Override
	final boolean matches( final String command ) {
		return NumericLiteralCommand.INSTANCE.matches( command );
	}
	
	@Override
	final void run(
		final JakRepl repl,
		final String command,
		final String[] args,
		final boolean isSolitary )
		throws IOException
	{
		ArrayList< ReplCommand > argCommands = new ArrayList< ReplCommand >( args.length );
		for ( String arg : args ) {
			//DQH - Operators have to come before Numerics to handle "-" case.
			if ( OperatorCommand.INSTANCE.matches( arg ) ) {
				argCommands.add( OperatorCommand.INSTANCE );
			} else if ( NumericLiteralCommand.INSTANCE.matches( arg ) ) {
				argCommands.add( NumericLiteralCommand.INSTANCE );
			} else {
				repl.console().printError( "Invalid expression" );
				return;
			}
		}
		
		NumericLiteralCommand.INSTANCE.run( repl, command, NO_ARGS, false );
		for ( int i = 0; i < args.length; ++i ) {
			String arg = args[ i ];
			ReplCommand argCommand = argCommands.get( i );
			
			argCommand.run( repl, arg, NO_ARGS, false );
		}
		if ( isSolitary ) {
			repl.runProgram();
		}
	}
}
