package net.dougqh.jak.repl;

import java.io.IOException;
import java.util.List;

final class ShowAliasCommand extends ReplCommand {
	static final ShowAliasCommand INSTANCE = new ShowAliasCommand();
	
	private ShowAliasCommand() {}
	
	@Override
	final boolean matches( final String command ) {
		try {
			StatePart.parse( command );
			return true;
		} catch ( IllegalArgumentException e ) {
			return false;
		}
	}
	
	@Override
	final boolean run(
		final JakRepl repl,
		final String command,
		final List< String > args )
		throws IOException
	{
		checkNoArguments( args );
		
		StatePart part = StatePart.parse( command );
		part.print( repl.lastState(), repl.console() );
		return true;
	}
}
