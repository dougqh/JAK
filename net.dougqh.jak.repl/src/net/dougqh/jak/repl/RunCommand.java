package net.dougqh.jak.repl;

import java.io.IOException;
import java.util.List;

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
		final List< String > args )
		throws IOException
	{
		checkNoArguments( args );
		
		repl.runProgram( false );
		return true;
	}
}
