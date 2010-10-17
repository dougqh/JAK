package net.dougqh.jak.repl;

import java.io.IOException;

final class EchoCommand extends FixedIdCommand {
	static final String ID = "echo";
	static final EchoCommand INSTANCE = new EchoCommand();
	
	private EchoCommand() {
		super( ID );
	}
	
	@Override
	final void run(
		final JakRepl repl,
		final String command,
		final String[] args,
		final boolean isSolitary )
		throws IOException
	{
		switch ( args.length ) {
			case 0:
			break;
			
			case 1:
			repl.config().echo( OnOff.parse( args[ 0 ] ).booleanValue() );
			break;
			
			default:
			repl.console().printUsage( ID, boolean.class );
			throw new IllegalStateException();
		}
		
		repl.console().append( "echo is " ).append( OnOff.valueOf( repl.config().echo() ) ).endl();
	}
}
