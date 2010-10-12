package net.dougqh.jak.repl;

import java.io.IOException;

final class ClearCommand extends ReplCommand {
	static final String ID = "clear";
	static final ClearCommand INSTANCE = new ClearCommand();
	
	private ClearCommand() {}
	
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
		
		repl.console().clear();
	}
}
