package net.dougqh.jak.repl;

import java.io.IOException;

final class RunCommand extends FixedIdCommand {
	public static final String ID = "run";
	public static final RunCommand INSTANCE = new RunCommand();
	
	private RunCommand() {
		super( ID );
	}
	
	@Override
	final boolean run(
		final JakRepl repl,
		final String command,
		final String[] args )
		throws IOException
	{
		repl.runProgram( false );
		return true;
	}
}
