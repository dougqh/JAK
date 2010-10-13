package net.dougqh.jak.repl;

import java.io.IOException;

abstract class ReplCommand {
	static final String[] NO_ARGS = {};
	
	abstract boolean matches( final String command );
	
	abstract void run(
		final JakRepl repl,
		final String command,
		final String[] args,
		final boolean isSolitary )
		throws IOException;

	boolean disableArgumentParsing() {
		return false;
	}
	
	protected static final void checkNoArguments( final String... args ) {
		if ( args.length != 0 ) {
			throw new IllegalArgumentException();
		}
	}
}
