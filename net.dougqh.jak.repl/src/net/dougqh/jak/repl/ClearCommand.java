package net.dougqh.jak.repl;

import java.io.IOException;

final class ClearCommand extends FixedIdCommand {
	static final String ID = "clear";
	static final ClearCommand INSTANCE = new ClearCommand();
	
	private ClearCommand() {
		super( ID );
	}
	
	@Override
	final boolean run(
		final JakRepl repl,
		final String command,
		final String[] args )
		throws IOException
	{
		checkNoArguments( args );
		
		repl.console().clear();
		return true;
	}
}
