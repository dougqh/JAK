package net.dougqh.jak.repl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class ArrayLiteralCommand extends ReplCommand {
	public static final ArrayLiteralCommand INSTANCE = new ArrayLiteralCommand();
	
	@Override
	boolean matches( final String command ) {
		return command.equals( "{" );
	}

	@Override
	final boolean run(
		final JakRepl repl,
		final String command,
		final List< String > args)
		throws IOException
	{
		List< String > elements;
		try {
			 elements = normalizeElements( args );
		} catch ( IllegalArgumentException e ) {
			repl.console().printError( e.getMessage() );
			return false;
		}
		
		List< Integer > ints = new ArrayList< Integer >( elements.size() );
		for ( String element : elements ) {
			try {
				ints.add( (Integer)ReplArgument.INT.parse( repl, element ) );
			} catch ( IllegalArgumentException e ) {
				repl.console().printError( e.getMessage() );
				return false;
			}
		}
		
		repl.codeWriter().iarray( ints );
		return true;
	}
	
	private static final List< String > normalizeElements(
		final List< String > args )
	{
		//TODO : Fixing this awful mis-mash of parsing logic
		String lastArg = args.get( args.size() - 1 );
		if ( ! lastArg.equals( "}" ) ) {
			throw new IllegalArgumentException( "Invalid array literal" );
		}
		
		List< String > normalizedArgs = new ArrayList< String >( args.size() - 1 );
		
		for ( String arg: args.subList( 0, args.size() - 1 ) ) {
			normalizedArgs.addAll( split( arg ) );
		}
		return normalizedArgs;
	}
	
	static final List< String > split( final String arg ) {
		String[] parts = arg.split( "," );
		for ( int i = 0; i < parts.length; ++i ) {
			parts[ i ] = parts[ i ].trim();
		}
		return Arrays.asList( parts );
	}
	
	@Override
	final boolean runProgramAfterCommand() {
		return true;
	}
}
