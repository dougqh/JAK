package net.dougqh.jak.repl;

import static net.dougqh.jak.JavaAssembler.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

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
		this.console = new ReplConsole();
		
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
		} else {
			Method method = findMethod( command );
			if ( method == null ) {
				this.console.printError( "Unknown command: " + command );
			} else {
				try {
					method.invoke( this.codeWriter );
				} catch ( IllegalArgumentException e ) {
					this.console.printError( "Invalid arguments" );
					this.printUsage( method );
				} catch ( IllegalAccessException e ) {
					throw new IllegalStateException( e );
				} catch ( InvocationTargetException e ) {
					throw new IllegalStateException( e );
				}
				this.runProgram();
			}
		}
	}
	
	private final void resetProgram() throws IOException {
		this.recorder.reset();
		//DQH - 10-10-2010 - Need to reinitialize the current writer 
		//because will already contain code replayed from the recorder.
		this.initNewWriter();
		
		this.console.clear();
	}
	
	private final void listProgram() throws IOException {
		this.recorder.list( this.console );
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

	private static final Method findMethod( final String command ) {
		for ( Method method : JavaCodeWriter.class.getMethods() ) {
			if ( isMatch( method, command ) ) {
				return method;
			}
		}
		return null;
	}
	
	private static final boolean isMatch(
		final Method method,
		final String command )
	{
		return method.getName().equals( command ) && isWritingMethod( method );
	}
	
	private static final boolean isWritingMethod( final Method method ) {
		return method.getDeclaringClass().equals( JavaCodeWriter.class ) &&
			method.getReturnType().equals( JavaCodeWriter.class );
	}	
	
	private final void printUsage( final Method method ) {
		this.console.append( "Usage: " );
		this.console.append( method.getName() );
		for ( Class< ? > type : method.getParameterTypes() ) {
			this.console.append( ' ' );
			this.console.append( type.getSimpleName() ); 
		}
		this.console.endl();
	}
}
