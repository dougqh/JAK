package net.dougqh.jak.repl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

abstract class ReplCommand {
	static final List< String > NO_ARGS = Collections.emptyList();
	
	abstract boolean matches( final String command );
	
	abstract boolean run(
		final JakRepl repl,
		final String command,
		final List< String > args )
		throws IOException;

	boolean runProgramAfterCommand() {
		return false;
	}
	
	protected static final void checkNoArguments( final List< String > args ) {
		if ( ! args.isEmpty() ) {
			throw new IllegalArgumentException();
		}
	}
}
