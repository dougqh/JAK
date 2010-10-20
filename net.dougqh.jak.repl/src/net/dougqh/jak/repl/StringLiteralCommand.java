package net.dougqh.jak.repl;

import java.io.IOException;

final class StringLiteralCommand extends ReplCommand {
	static final StringLiteralCommand INSTANCE = new StringLiteralCommand();
	
	private StringLiteralCommand() {}
	
	@Override
	final boolean disableArgumentParsing() {
		return true;
	}
	
	@Override
	final boolean matches( final String command ) {
		char firstChar = command.charAt( 0 );
		return  ( firstChar == ReplArgument.STRING_QUOTE );
	}
	
	@Override
	final boolean run(
		final JakRepl repl,
		final String command,
		final String[] args )
		throws IOException
	{
		try {
			String literal = (String)ReplArgument.STRING_LITERAL.parse( command );
			repl.codeWriter().ldc( literal );
			
			return true;
		} catch ( IllegalArgumentException e ) {
			repl.console().printError( "Invalid literal" );
			return false;
		}
	}
}
