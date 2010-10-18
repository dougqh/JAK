package net.dougqh.jak.repl;

import java.io.IOException;

abstract class ConfigCommand extends FixedIdCommand {
	ConfigCommand( final String id ) {
		super( id );
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
			this.set( repl.config(), OnOff.parse( args[ 0 ] ).booleanValue() );
			break;
			
			default:
			repl.console().printUsage( this.id, OnOff.class );
			throw new IllegalStateException();
		}
		
		repl.console().
			append( this.id + " is " ).append( OnOff.valueOf( this.get( repl.config() ) ) ).
			endl();
	}
	
	abstract void set( final ReplConfig config, final boolean value );
	
	abstract boolean get( final ReplConfig config );
}
