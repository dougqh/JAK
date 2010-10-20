package net.dougqh.jak.repl;

final class AutoRunCommand extends ConfigCommand {
	public static final String ID = "autoRun";
	public static final AutoRunCommand INSTANCE = new AutoRunCommand();
	
	private AutoRunCommand() {
		super( ID );
	}
	
	@Override
	final boolean get( final ReplConfig config ) {
		return config.autoRun();
	}
	
	@Override
	final void set( final ReplConfig config, final boolean value ) {
		config.autoRun( value );
	}
}
