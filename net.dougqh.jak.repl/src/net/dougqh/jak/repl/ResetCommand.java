package net.dougqh.jak.repl;

import java.io.IOException;

final class ResetCommand extends ReplCommand {
	static final String ID = "reset";
	static final ResetCommand INSTANCE = new ResetCommand();
	
	private ResetCommand() {}
	
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
		
		repl.resetProgram();
	}
}
