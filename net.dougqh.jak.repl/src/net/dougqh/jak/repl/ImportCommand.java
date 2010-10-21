package net.dougqh.jak.repl;

import java.io.IOException;

final class ImportCommand extends FixedIdCommand {
	public static final String ID = "import";
	public static final ImportCommand INSTANCE = new ImportCommand();
	
	private ImportCommand() {
		super( ID );
	}
	
	@Override
	final boolean run(
		final JakRepl repl,
		final String command,
		final String[] args )
		throws IOException
	{
		if ( args.length != 1 ) {
			repl.console().printUsage( ID, String.class );
		}
		
		try {
			repl.imports().addImport( args[ 0 ] );
			return true;
		} catch ( ClassNotFoundException e ) {
			repl.console().printError( e.getMessage() );
			return false;
		}
	}
}
