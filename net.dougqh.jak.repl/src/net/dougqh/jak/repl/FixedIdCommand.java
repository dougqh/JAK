package net.dougqh.jak.repl;

abstract class FixedIdCommand extends ReplCommand {
	protected final String id;
	
	FixedIdCommand( final String id ) {
		this.id = id;
	}
	
	@Override
	final boolean matches( final String command ) {
		return this.id.equals( command );
	}
}
