package net.dougqh.jak.repl;

import static net.dougqh.jak.Jak.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jline.Completor;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.jvm.assembler.JakConfiguration;
import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmCodeWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;

public final class JakRepl {
	public static void main( final String[] args ) throws IOException {
		JakRepl repl = new JakRepl();
		switch ( args.length ) {
			case 0:
			break;
			
			case 1:
			repl.initRecordingDir( new File( args[ 0 ] ) );
			break;
			
			default:
			System.err.println( "Invalid usage!" );
			System.err.println( "Usage: java -jar net.dougqh.jak.repl.jar [recording-dir]" );
			System.exit( -1 );
			break;
		}
		repl.run();
	}
	
	static final String SHORT_CLASS = "Generated";
	static final String CLASS = "net.dougqh.jak.repl." + SHORT_CLASS;
	static final JavaMethodDescriptor MAIN_METHOD = 
		public_().static_().final_().method( void.class, "main" );
	
	private static final String EXIT = "exit";
		
	private final ReplRecorder recorder;
	private final ReplConsole console;
	
	private final ReplConfig config = new ReplConfig();
	private final ReplImports imports = new ReplImports();
	
	private int suppressionCount = 0;
	private boolean isReplaying = false;
	
	private JvmClassWriter classWriter = null;
	private JvmCodeWriter codeWriter = null;
	private ReplStateCodeWriter replStateCodeWriter = null;
	private ReplState lastState = new ReplState();
	
	private File recordingDir = null;
	
	private List< ReplCommand > commands = Arrays.asList(
		AutoRunCommand.INSTANCE,
		RunCommand.INSTANCE,
		AutoShowCommand.INSTANCE,
		ShowCommand.INSTANCE,
		ShowAliasCommand.INSTANCE,
		ResetCommand.INSTANCE,
		ClearCommand.INSTANCE,
		ListCommand.INSTANCE,
		EchoCommand.INSTANCE,
		ImportCommand.INSTANCE,
		ImportsCommand.INSTANCE,
		ExpressionCommand.INSTANCE,
		ArrayLiteralCommand.INSTANCE,
		StringLiteralCommand.INSTANCE,
		OperatorCommand.INSTANCE );
	
	public JakRepl() throws IOException {
		this.recorder = new ReplRecorder();
		this.console = new ReplConsole().addCompletor( new ReplCompletor() );
		
		this.initNewWriter();
	}
	
	final void initRecordingDir( final File dir ) {
		this.recordingDir = dir;
	}
	
	final Type thisType() {
		return this.classWriter.thisType();
	}
	
	final ReplRecorder recorder() {
		return this.recorder;
	}
	
	final ReplConsole console() {
		return this.console;
	}
	
	final ReplConfig config() {
		return this.config;
	}
	
	final ReplImports imports() {
		return this.imports;
	}
	
	final JvmCodeWriter codeWriter() {
		return this.codeWriter;
	}
	
	final ReplStateCodeWriter stateCodeWriter() {
		return this.replStateCodeWriter;
	}
	
	final ReplState lastState() {
		return this.lastState;
	}
	
	final boolean isRecordingEnabled() {
		return ( this.suppressionCount == 0 );
	}
	
	final void suppressRecording() {
		++this.suppressionCount;
	}
	
	final void restoreRecording() {
		--this.suppressionCount;
	}
	
	final void startReplay() {
		this.suppressRecording();
		this.isReplaying = true;
	}
	
	final void endReplay() {
		this.restoreRecording();
		this.isReplaying = false;
	}
	
	final boolean isReplaying() {
		return this.isReplaying;
	}

	public final void run() {
		this.console.install();
		try {
			for ( String command = this.console.readCommand();
				! isExit( command );
				command = this.console.readCommand() )
			{
				if ( Thread.currentThread().isInterrupted() ) {
					this.console.printError( "Thread interrupted - exiting..." );
				}
				
				this.processLine( command );
			}
		} catch ( IOException e ) {
			try {
				this.console.printError( "Unable to read from console - exiting..." );
			} catch ( IOException e2 ) {
				//exiting anyway, so swallow it...
			}
		}  finally {
			this.console.uninstall();
		}
	}
	
	private final void processLine( final String commandLine ) throws IOException {
		if ( commandLine == null || commandLine.isEmpty() ) {
			return;
		}
		
		String[] commands = commandLine.split( ";" );

		this.recorder.checkpoint();
		try {
			boolean needsRun = false;
			for ( String command : commands ) {
				command = command.trim();
				if ( command.isEmpty() ) {
					continue;
				}
				
				//Intentionally not short-circuited
				needsRun = needsRun | this.processCommand( command ); 
			}
			if ( needsRun && this.config.autoRun() ) {
				this.runProgram( true );
			}
		} catch ( Error e ) {
			this.console().printError( e.getMessage() );
			this.recorder.rollback();
		}
	}
	
	private final boolean processCommand( final String fullCommand ) throws IOException {
		ParsedCommand parsedCommand = parseCommand( fullCommand );
		
		ReplCommand replCommand = findCommand( parsedCommand.command );
		boolean success = replCommand.run( this, parsedCommand.command, parsedCommand.arguments );
		return success && replCommand.runProgramAfterCommand();
	}
	
	private static final ParsedCommand parseCommand( final String fullCommand ) {
		if ( isStringStart( fullCommand ) && isStringEnd( fullCommand ) ) {
			return new ParsedCommand( fullCommand, ReplCommand.NO_ARGS );
		} else {
			String[] commandParts = fullCommand.split( " " );

			String command = commandParts[ 0 ];
			List< String > arguments = parseArguments( commandParts );

			return new ParsedCommand( command, arguments );
		}
	}
	
	private static final List< String > parseArguments( final String[] commandParts ) {
		if ( commandParts.length == 1 ) {
			return Collections.emptyList();
		}

		ArrayList< String > args = new ArrayList< String >( commandParts.length - 1 );

		StringBuilder stringUnderConstruction = null;
		for ( int i = 1; i < commandParts.length; ++i ) {
			String arg = commandParts[ i ];
			
			if ( stringUnderConstruction == null ) {
				if ( isStringStart( arg ) && ! isStringEnd( arg ) ) {
					stringUnderConstruction = new StringBuilder();
					stringUnderConstruction.append( arg );
				} else {
					args.add( arg );
				}
			} else {
				stringUnderConstruction.append( ' ' ).append( arg );
				
				if ( isStringEnd( arg ) ) {
					args.add( stringUnderConstruction.toString() );
					stringUnderConstruction = null;
				}
			}
		}
		if ( stringUnderConstruction != null ) {
			throw new IllegalArgumentException();
		}
		return Collections.unmodifiableList( args );
	}
	
	private static final boolean isStringStart( final String arg ) {
		char firstChar = arg.charAt( 0 );
		return ( firstChar == ReplArgument.STRING_QUOTE );
	}
	
	private static final boolean isStringEnd( final String arg ) {
		char lastChar = arg.charAt( arg.length() - 1 );
		return ( lastChar == ReplArgument.STRING_QUOTE );
	}
	
	private final ReplCommand findCommand( final String command ) {
		for ( ReplCommand replCommand : commands ) {
			if ( replCommand.matches( command ) ) {
				return replCommand;
			}
		}
		return  MethodCommand.INSTANCE;
	}
	
	final void resetProgram() throws IOException {
		this.recorder.reset();
		//DQH - 10-10-2010 - Need to reinitialize the current writer 
		//because the current writer will already contain code replayed 
		//from the recorder.
		this.initNewWriter();
		
		this.console.clear();
	}
	
	final void runProgram( final boolean isAuto ) throws IOException {
		this.suppressRecording();
		try {
			this.codeWriter.return_();
		} finally {
			this.restoreRecording();
		}
		
		if ( this.recordingDir != null ) {
			File historyFile = new File(
				this.recordingDir, 
				SHORT_CLASS + "-" + this.recorder.size() + ".class" );
			
			FileOutputStream out = new FileOutputStream( historyFile );
			try {
				this.classWriter.writeTo( out );
			} finally {
				out.close();
			}
		}
		
		try {
			//TODO: set size properly
			ReplState state = new ReplState(
				Math.max( this.codeWriter().stackMonitor().maxStack(), 1 ),
				this.codeWriter().localsMonitor().maxLocals() );
			
			this.runImpl( state );
			
			if ( this.config.echo() ) {
				this.console.endl();
			}
			if ( this.config.autoShowStack() ) {
				state.printStack( this.console );
			}
			if ( this.config.autoShowStack() && this.config.autoShowLocals() ) {
				this.console.println();
			}
			if ( this.config.autoShowLocals() ) {
				state.printLocals( this.console );
			}
			
			this.lastState = state;
			this.recorder().checkpoint();
		} catch ( Error e ) {
			this.console().printError( e.getMessage() );
			if ( ! isAuto ) {
				this.console.printNotice( "Rolling back code to previous successful run." );
			}
			this.recorder().rollback();
		}
		
		this.initNewWriter();
	}

	private void runImpl( final ReplState state ) {
		Class< ? > generatedClass = this.classWriter.load();
		
		try {
			Field stackField = generatedClass.getDeclaredField(
				ReplStateCodeWriter.STATE_FIELD.getName() );
			stackField.set( null, state );
		} catch ( SecurityException e ) {
			throw new IllegalStateException( e );
		} catch ( NoSuchFieldException e ) {
			throw new IllegalStateException( e );
		} catch ( IllegalArgumentException e ) {
			throw new IllegalStateException( e );
		} catch ( IllegalAccessException e ) {
			throw new IllegalStateException( e );
		}
		
		try {
			Method mainMethod = generatedClass.getDeclaredMethod( MAIN_METHOD.getName() );
			mainMethod.invoke( null );
		} catch ( SecurityException e ) {
			throw new IllegalStateException( e );
		} catch ( NoSuchMethodException e ) {
			throw new IllegalStateException( e );
		} catch ( IllegalArgumentException e ) {
			throw new IllegalStateException( e );
		} catch ( IllegalAccessException e ) {
			throw new IllegalStateException( e );
		} catch ( InvocationTargetException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	private final void initNewWriter() {		
		JakConfiguration config = new JakConfiguration().
			enableTypeTracking().
			monitor( new ReplMonitor( this ) );
		
		this.classWriter = new JvmWriter().define( public_().final_().class_( CLASS ) );
		this.classWriter.initConfig( config );
		
		this.classWriter.define( ReplStateCodeWriter.STATE_FIELD );
		
		//DQH - 10-10-2010 - It is crucial that this.codeWriter be set 
		//before replay is called because ReplStackMonitor and 
		//ReplLocalsMonitor will make calls to JakRepl.codeWriter()
		//which need to return the newest JavaCodeWriter.
		//Previous implementations erroneously returned the previous 
		//JavaCodeWriter during replay which did not produce the desired 
		//results.
		this.codeWriter = this.classWriter.define( MAIN_METHOD );
		this.replStateCodeWriter = new ReplStateCodeWriter( this );
		
		this.startReplay();
		try {
			this.recorder.replay( this.classWriter, this.codeWriter );
		} finally {
			this.endReplay();
		}
	}
	
	private static final boolean isExit( final String command ) {
		return EXIT.equals( command );
	}
	
	private static final class ReplCompletor implements Completor {
		@SuppressWarnings( { "unchecked", "rawtypes" } ) 
		@Override
		public final int complete(
			final String buffer,
			final int cursor,
			final List candidates )
		{
			candidates.addAll( ReplMethod.findLike( buffer ) );
			return cursor;
		}
	}
	
	private static final class ParsedCommand {
		final String command;
		final List< String > arguments;
		
		ParsedCommand( final String command, final List< String > arguments ) {
			this.command = command;
			this.arguments = arguments;
		}
	}
}
