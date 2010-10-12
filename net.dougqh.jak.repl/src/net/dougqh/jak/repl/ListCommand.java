package net.dougqh.jak.repl;

import java.io.IOException;

final class ListCommand extends FixedIdCommand {
	static final String ID = "list";
	static final ListCommand INSTANCE = new ListCommand();
	
	private ListCommand() {
		super( ID );
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
