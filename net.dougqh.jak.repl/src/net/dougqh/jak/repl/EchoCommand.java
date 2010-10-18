package net.dougqh.jak.repl;


final class EchoCommand extends ConfigCommand {
	static final String ID = "echo";
	static final EchoCommand INSTANCE = new EchoCommand();
	
	private EchoCommand() {
		super( ID );
	}
	
	@Override
	final void set( final ReplConfig config, final boolean value ) {
		config.echo( value );
	}
	
	@Override
	final boolean get( final ReplConfig config ) {
		return config.echo();
	}
}
