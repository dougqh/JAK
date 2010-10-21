package net.dougqh.jak.repl;

import java.io.IOException;
import java.util.List;

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
		final List< String > args )
		throws IOException
	{
		if ( args.size() != 1 ) {
			repl.console().printUsage( ID, String.class );
			return false;
		}
		
		try {
			repl.imports().addImport( args.get( 0 ) );
			return true;
		} catch ( ClassNotFoundException e ) {
			repl.console().printError( e.getMessage() );
			return false;
		}
	}
}
