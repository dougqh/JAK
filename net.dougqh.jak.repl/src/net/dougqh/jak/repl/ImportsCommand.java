package net.dougqh.jak.repl;

import java.io.IOException;

final class ImportsCommand extends FixedIdCommand {
	public static final String ID = "imports";
	public static final ImportsCommand INSTANCE = new ImportsCommand();
	
	private ImportsCommand() {
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
		
		repl.imports().print( repl.console() );
		return true;
	}
}
