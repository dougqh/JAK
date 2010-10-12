package net.dougqh.jak.repl;

import static net.dougqh.jak.JavaAssembler.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import jline.Completor;
import net.dougqh.jak.JavaClassWriter;
import net.dougqh.jak.JavaCodeWriter;
import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.JavaFieldDescriptor;
import net.dougqh.jak.JavaMethodDescriptor;


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
	static final JavaFieldDescriptor STATE_FIELD = 
		public_().static_().field( ReplState.class, "state" );
	static final JavaMethodDescriptor MAIN_METHOD = 
		public_().static_().final_().method( void.class, "main" );
	
	private static final String RESET = "reset";
	private static final String LIST = "list";
	private static final String CLEAR = "clear";
	private static final String EXIT = "exit";
		
	private final ReplRecorder recorder;
	private final ReplConsole console;
	
	private int suppressionCount = 0;
	private boolean isReplaying = false;
	
	private JavaClassWriter classWriter = null;
	private JavaCodeWriter codeWriter = null;
	
	private File recordingDir = null;
	
	public JakRepl() throws IOException {
		this.recorder = new ReplRecorder();
		this.console = new ReplConsole().addCompletor( new ReplCompletor() );
		
		this.initNewWriter();
	}
	
	final void initRecordingDir( final File dir ) {
		this.recordingDir = dir;
	}
	
	protected final Type thisType() {
		return this.classWriter.thisType();
	}
	
	protected final ReplRecorder recorder() {
		return this.recorder;
	}
	
	protected final ReplConsole console() {
		return this.console;
	}
	
	protected final JavaCodeWriter codeWriter() {
		return this.codeWriter;
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
	
	final void replay( final JavaCoreCodeWriter writer ) {
		this.startReplay();
		try {
			this.recorder.replay( writer );
		} finally {
			this.endReplay();
		}
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
				
				this.processCommand( command );
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
	
	private final void processCommand( final String command ) throws IOException {
		if ( command.equals( RESET ) ) {
			this.resetProgram();
		} else if ( command.equals( LIST ) ) {
			this.listProgram();
		} else if ( command.equals( CLEAR ) ) {
			this.clearConsole();
		} else if ( isLiteral( command ) ) {
			this.literal( command );
		} else {
			this.invokeMethod( command );
		}
	}
	
	private final void clearConsole() throws IOException {
		this.console.clear();
	}
	
	private final void resetProgram() throws IOException {
		this.recorder.reset();
		//DQH - 10-10-2010 - Need to reinitialize the current writer 
		//because the current writer will already contain code replayed 
		//from the recorder.
		this.initNewWriter();
		
		this.console.clear();
	}
	
	private final void listProgram() throws IOException {
		this.recorder.list( this.console );
	}
	
	private static final boolean isLiteral( final String command ) {
		char firstChar = command.charAt( 0 );
		return ( firstChar == '-' ) ||
			( firstChar == ReplArgument.CHAR_QUOTE ) ||
			( firstChar == ReplArgument.STRING_QUOTE ) ||
			Character.isDigit( firstChar ) ||
			isBooleanLiteral( command );
	}

	private static final boolean isBooleanLiteral( final String command ) {
		return ReplArgument.TRUE.equals( command ) ||
			ReplArgument.FALSE.equals( command );
	}
	
	private final void literal( final String command ) throws IOException {
		try {
			char firstChar = command.charAt( 0 );
			if ( firstChar == ReplArgument.CHAR_QUOTE ) {
				Character literal = (Character)ReplArgument.CHAR.parse( command );
				this.codeWriter.iconst( literal );
			} else if ( firstChar == ReplArgument.STRING_QUOTE ) {
				String literal = (String)ReplArgument.STRING_LITERAL.parse( command );
				this.codeWriter.ldc( literal );
			} else if ( isBooleanLiteral( command ) ) {
				Boolean literal = (Boolean)ReplArgument.BOOLEAN.parse( command );
				this.codeWriter.iconst( literal );
			} else {
				Class< ? > type = ReplArgument.typeQualifier( command );
				if ( type == null ) {
					Integer value = (Integer)ReplArgument.INT.parse( command );
					this.codeWriter.iconst( value );
				} else if ( type.equals( float.class ) ) {
					Float value = (Float)ReplArgument.FLOAT.parse( command );
					this.codeWriter.fconst( value );
				} else if ( type.equals( long.class ) ) {
					Long value = (Long)ReplArgument.LONG.parse( command );
					this.codeWriter.lconst( value );
				} else if ( type.equals( double.class ) ) {
					Double value = (Double)ReplArgument.DOUBLE.parse( command );
					this.codeWriter.dconst( value );
				} else {
					throw new IllegalStateException();
				}
			}
			this.runProgram();
		} catch ( IllegalArgumentException e ) {
			this.console.printError( "Invalid literal" );
		}
	}
	
	private final void invokeMethod( final String command )
		throws IOException
	{
		String[] commandParts = command.split( " " );

		String methodName = commandParts[ 0 ];
		String[] argStrings = Arrays.copyOfRange( commandParts, 1, commandParts.length );
		
		Set< ReplMethod > methods = ReplMethod.find( methodName );
		if ( methods.isEmpty() ) {
			this.console.printError( "Unknown command: " + command );
			this.console.complete( methodName );
		} else {
			//DQH - 10-11-2010 - Pretty ugly, loop over matching methods 
			//until an implementations that has matching arguments is found.
			//If no match is found, then print out usage.
			boolean foundMatch = false;
			for ( ReplMethod method : methods ) {
				try {
					Object[] args = method.parseArguments( argStrings );
					method.invoke( this.codeWriter, args );
					this.runProgram();

					foundMatch = true;
					break;
				} catch ( IllegalArgumentException e ) {
				}				
			}
			if ( ! foundMatch ) {
				this.console.printError( "Invalid arguments" );
				for ( ReplMethod method : methods ) {
					this.printUsage( method );
				}
			}
		}		
	}
	
	private final void runProgram() throws IOException {
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
		
		//TODO: set size properly
		ReplState state = new ReplState( 32 );
		
		Class< ? > generatedClass = this.classWriter.load();
		
		try {
			Field stackField = generatedClass.getDeclaredField( STATE_FIELD.getName() );
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
		
		state.print( this.console );
		
		this.initNewWriter();
	}
	
	private final void initNewWriter() {		
		this.classWriter = define( public_().final_().class_( CLASS ) );
		this.classWriter.monitor( new ReplMonitor( this ) );
		
		this.classWriter.define( STATE_FIELD );
		
		//DQH - 10-10-2010 - It is crucial that this.codeWriter be set 
		//before replay is called because ReplStack will make calls 
		//to JakRepl.codeWriter() which needs to return the newest 
		//JavaCodeWriter.
		//Previous implementations erroneously returned the previous 
		//JavaCodeWriter during replay which did not produce the desired 
		//results.
		this.codeWriter = this.classWriter.define( MAIN_METHOD );
		this.replay( this.codeWriter.coreWriter() );
	}
	
	private static final boolean isExit( final String command ) {
		return EXIT.equals( command );
	}	
	
	private final void printUsage( final ReplMethod method ) {
		this.console.append( "Usage: " );
		this.console.append( method.getName() );
		for ( ReplArgument argument : method.getArguments() ) {
			this.console.append( ' ' );
			this.console.append( argument.getTypeName() ); 
		}
		this.console.endl();
	}
	
	private final class ReplCompletor implements Completor {
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
}
