package net.dougqh.jak.repl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

final class ExpressionCommand extends ReplCommand {
	static final ExpressionCommand INSTANCE = new ExpressionCommand();
	
	private ExpressionCommand() {}
	
	@Override
	final boolean matches( final String command ) {
		return NumericLiteralCommand.INSTANCE.matches( command );
	}
	
	@Override
	final boolean runProgramAfterCommand() {
		return true;
	}
	
	@Override
	final boolean run(
		final JakRepl repl,
		final String command,
		final List< String > args )
		throws IOException
	{
		ArrayList< ReplCommand > argCommands = new ArrayList< ReplCommand >( args.size() );
		for ( String arg : args ) {
			//DQH - Operators have to come before Numerics to handle "-" case.
			if ( OperatorCommand.INSTANCE.matches( arg ) ) {
				argCommands.add( OperatorCommand.INSTANCE );
			} else if ( NumericLiteralCommand.INSTANCE.matches( arg ) ) {
				argCommands.add( NumericLiteralCommand.INSTANCE );
			} else {
				repl.console().printError( "Invalid expression" );
				return false;
			}
		}
		
		NumericLiteralCommand.INSTANCE.run( repl, command, NO_ARGS );
		//DQH - TODO: Consider switching to iterators
		for ( int i = 0; i < args.size(); ++i ) {
			String arg = args.get( i );
			ReplCommand argCommand = argCommands.get( i );
			
			argCommand.run( repl, arg, NO_ARGS );
		}
		return true;
	}
}
