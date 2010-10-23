package net.dougqh.jak.repl;

import java.io.IOException;
import java.util.List;

final class AutoShowCommand extends FixedIdCommand {
	static final String ID = "autoShow";
	static final AutoShowCommand INSTANCE = new AutoShowCommand();
	
	private AutoShowCommand() {
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
					printStatus( repl, StatePart.STACK );
					printStatus( repl, StatePart.LOCALS );
					return true;
				}
				
				case 1: {
					StatePart part = StatePart.parse( args.get( 0 ) );
					printStatus( repl, part );
					return true;
				}
				
				case 2: {
					StatePart part = StatePart.parse( args.get( 0 ) );
					OnOff onOff = OnOff.parse( args.get( 1 ) );
					set( repl, part, onOff );
					printStatus( repl, part );
					return true;
				}
				
				default: {
					throw new IllegalArgumentException();
				}
			}
		} catch ( IllegalArgumentException e ) {
			repl.console().printUsage( ID );
			repl.console().printUsage( ID, StatePart.class );
			repl.console().printUsage( ID, StatePart.class, OnOff.class );
			return false;
		}
	}
	
	private static final void set( final JakRepl repl, final StatePart part, final OnOff onOff ) {
		part.set( repl.config(), onOff.booleanValue() );
	}
	
	private static final void printStatus( final JakRepl repl, final StatePart part ) {
		repl.console().
			append( ID ).append( ' ' ).append( part ).append( " is " ).
			append( OnOff.valueOf( part.get( repl.config() ) ) ).endl();
	}
}
