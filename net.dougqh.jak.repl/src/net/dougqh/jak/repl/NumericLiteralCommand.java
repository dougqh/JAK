package net.dougqh.jak.repl;

import java.io.IOException;

public final class NumericLiteralCommand extends ReplCommand {
	static final NumericLiteralCommand INSTANCE = new NumericLiteralCommand();
	
	private NumericLiteralCommand() {}
	
	@Override
	final boolean matches( final String command ) {
		char firstChar = command.charAt( 0 );
		return ( firstChar == '-' ) ||
			( firstChar == ReplArgument.CHAR_QUOTE ) ||
			Character.isDigit( firstChar ) ||
			isBooleanLiteral( command );
	}
	
	private static final boolean isBooleanLiteral( final String command ) {
		return ReplArgument.TRUE.equals( command ) ||
			ReplArgument.FALSE.equals( command );
	}
	
	@Override
	final void run(
		final JakRepl repl,
		final String command,
		final String[] args,
		final boolean isSolitary )
		throws IOException
	{
		try {
			char firstChar = command.charAt( 0 );
			if ( firstChar == ReplArgument.CHAR_QUOTE ) {
				Character literal = (Character)ReplArgument.CHAR.parse( command );
				repl.codeWriter().iconst( literal );
			} else if ( isBooleanLiteral( command ) ) {
				Boolean literal = (Boolean)ReplArgument.BOOLEAN.parse( command );
				repl.codeWriter().iconst( literal );
			} else {
				Class< ? > type = ReplArgument.typeQualifier( command );
				if ( type == null ) {
					Integer value = (Integer)ReplArgument.INT.parse( command );
					repl.codeWriter().iconst( value );
				} else if ( type.equals( float.class ) ) {
					Float value = (Float)ReplArgument.FLOAT.parse( command );
					repl.codeWriter().fconst( value );
				} else if ( type.equals( long.class ) ) {
					Long value = (Long)ReplArgument.LONG.parse( command );
					repl.codeWriter().lconst( value );
				} else if ( type.equals( double.class ) ) {
					Double value = (Double)ReplArgument.DOUBLE.parse( command );
					repl.codeWriter().dconst( value );
				} else {
					throw new IllegalStateException();
				}
			}
			if ( isSolitary ) {
				repl.runProgram();
			}
		} catch ( IllegalArgumentException e ) {
			repl.console().printError( "Invalid literal" );
		}
	}
}
