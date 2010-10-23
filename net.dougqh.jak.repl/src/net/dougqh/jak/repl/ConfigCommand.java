package net.dougqh.jak.repl;

import java.io.IOException;
import java.util.List;

abstract class ConfigCommand extends FixedIdCommand {
	ConfigCommand( final String id ) {
		super( id );
	}
	
	@Override
	final boolean run(
		final JakRepl repl,
		final String command,
		final List< String > args )
		throws IOException
	{
		switch ( args.size() ) {
			case 0: {
				break;
			}
			
			case 1: {
				this.set( repl.config(), OnOff.parse( args.get( 0 ) ).booleanValue() );
				break;
			}
			
			default: {
				repl.console().printUsage( this.id );
				repl.console().printUsage( this.id, OnOff.class );
				return false;
			}
		}
		
		repl.console().
			append( this.id + " is " ).append( OnOff.valueOf( this.get( repl.config() ) ) ).
			endl();
		
		return true;
	}
	
	abstract void set( final ReplConfig config, final boolean value );
	
	abstract boolean get( final ReplConfig config );
}
