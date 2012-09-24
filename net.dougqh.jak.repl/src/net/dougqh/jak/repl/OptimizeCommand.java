package net.dougqh.jak.repl;

import java.io.IOException;
import java.util.List;

public final class OptimizeCommand extends FixedIdCommand {
	static final String ID = "optimize";
	static final OptimizeCommand INSTANCE = new OptimizeCommand();
	
	public OptimizeCommand() {
		super( ID );
	}
	
	@Override
	final boolean run(
		final JakRepl repl,
		final String command,
		final List<String> args)
		throws IOException
	{
		repl.recorder().optimize();
		repl.recorder().list( repl.console() );
		return true;
	}
	
	@Override
	final boolean runProgramAfterCommand() {
		return false;
	}
}
