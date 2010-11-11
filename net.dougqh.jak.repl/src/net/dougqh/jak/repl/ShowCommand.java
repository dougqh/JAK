package net.dougqh.jak.repl;

import java.io.IOException;
import java.util.List;

final class ShowCommand extends FixedIdCommand {
	static final String ID = "show";
	static final ShowCommand INSTANCE = new ShowCommand();
	
	private ShowCommand() {
		super( ID );
	}
	
	@Override
	final boolean run(
		final JakRepl repl,
		final String command,
		final List< String > args )
		throws IOException
	{
		try {
			switch ( args.size() ) {
				case 0: {
					repl.lastState().printStack( repl.console() );
					repl.console().println();
					repl.lastState().printLocals( repl.console() );
					return true;
				}
				
				case 1: {
					StatePart part = StatePart.parse( args.get( 0 ) );
					part.print( repl.lastState(), repl.console() );
					return true;
				}
				
				default: {
					throw new IllegalArgumentException();
				}
			}
		} catch ( IllegalArgumentException e ) {
			repl.console().printUsage( ID );
			repl.console().printUsage( ID, StatePart.class );
			return false;
		}
	}
}
