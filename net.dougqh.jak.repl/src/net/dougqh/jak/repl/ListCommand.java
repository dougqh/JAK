package net.dougqh.jak.repl;

import java.io.IOException;

final class ListCommand extends ReplCommand {
	static final String ID = "list";
	static final ListCommand INSTANCE = new ListCommand();
	
	private ListCommand() {}
	
	@Override
	final boolean matches( final String command ) {
		return command.equals( ID );
	}
	
	@Override
	final void run(
		final JakRepl repl,
		final String command,
		final String... args )
		throws IOException
	{
		checkNoArguments( args );
		
		repl.recorder().list( repl.console() );
	}	
}
