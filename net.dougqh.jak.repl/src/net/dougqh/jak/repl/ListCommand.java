package net.dougqh.jak.repl;

import java.io.IOException;
import java.util.List;

final class ListCommand extends FixedIdCommand {
	static final String ID = "list";
	static final ListCommand INSTANCE = new ListCommand();
	
	private ListCommand() {
		super( ID );
	}
	
	@Override
	final boolean run(
		final JakRepl repl,
		final String command,
		final List< String > args )
		throws IOException
	{
		checkNoArguments( args );
		
		repl.recorder().list( repl.console() );
		return true;
	}	
}
