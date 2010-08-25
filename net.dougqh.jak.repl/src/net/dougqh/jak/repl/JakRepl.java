package net.dougqh.jak.repl;

import static net.dougqh.jak.JavaAssembler.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

import jline.ConsoleReader;
import jline.ConsoleReaderInputStream;
import jline.History;
import net.dougqh.jak.JakMonitor;
import net.dougqh.jak.JavaAssembler;
import net.dougqh.jak.JavaClassWriter;
import net.dougqh.jak.JavaCodeWriter;
import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.Locals;
import net.dougqh.jak.Stack;
import net.dougqh.jak.JavaCoreCodeWriter.Jump;
import net.dougqh.java.meta.types.JavaTypes;
import net.dougqh.reflection.Delegate;


public final class JakRepl {
	public static void main( final String[] args ) throws IOException {
		new JakRepl().run();
	}
	
	private static final String CLASS = "net.dougqh.jak.repl.Generated";
	private static final String STACK_FIELD = "stack";
	private static final String MAIN_METHOD = "main";
	
	private static final String EXIT = "exit";
	private static final String RUN = "run";
	
	private final ConsoleReader consoleReader;
	
	private final ReplMonitor monitor;
	
	private JavaClassWriter classWriter;
	private JavaCodeWriter codeWriter;
	
	private boolean suppressRecording = false;
	
	private final ArrayList< Method > methods = new ArrayList< Method >( 32 );
	private final ArrayList< Object[] > args = new ArrayList< Object[] >( 32 );
	
	public JakRepl() throws IOException {
		File tempFile = File.createTempFile( "JavaAssemblerRepl", ".history" );
		
		this.consoleReader = new ConsoleReader();
		this.consoleReader.setHistory( new History( tempFile ) );
//		this.consoleReader.
//		this.consoleReader.setCompletionHandler( new ReplCompletionHandler() );
//		this.consoleReader.addCompletor(
		
		this.monitor = new ReplMonitor();
		
		this.initNewWriter();
	}
	
	public final void run() {
		ConsoleReaderInputStream.setIn( this.consoleReader );
		try {
			for ( String command = this.readCommand();
				! isExit( command );
				command = this.readCommand() )
			{
				if ( Thread.currentThread().isInterrupted() ) {
					this.printError( "Thread interrupted - exiting..." );
				}
				
				this.processCommand( command );
			}
		} catch ( IOException e ) {
			try {
				this.printError( "Unable to read from console - exiting..." );
			} catch ( IOException e2 ) {
				//exiting anyway, so swallow it...
			}
		}  finally {
			ConsoleReaderInputStream.restoreIn();
		}
	}
	
	private final void processCommand( final String command ) throws IOException {
		if ( command.equals( RUN ) ) {
			this.runProgram();
		} else {
			Method method = findMethod( command );
			if ( method == null ) {
				this.printError( "Unknown command: " + command );
			} else {
				try {
					method.invoke( this.codeWriter );
				} catch ( IllegalArgumentException e ) {
					throw new IllegalStateException( e );
				} catch ( IllegalAccessException e ) {
					throw new IllegalStateException( e );
				} catch ( InvocationTargetException e ) {
					throw new IllegalStateException( e );
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
		
		//TODO: set size properly
		ReplStack stack = new ReplStack( 32 );
		
		Class< ? > generatedClass = this.classWriter.load();
		
		try {
			Field stackField = generatedClass.getDeclaredField( STACK_FIELD );
			stackField.set( null, stack );
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
			Method mainMethod = generatedClass.getDeclaredMethod( MAIN_METHOD );
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
		
		stack.print( this.consoleReader );
		
		this.initNewWriter();
	}
	
	private final void initNewWriter() {
		this.classWriter = define( public_().final_().class_( CLASS ) );
		this.classWriter.monitor( this.monitor );
		
		this.classWriter.define( public_().static_().field( ReplStack.class, STACK_FIELD ) );
		
		this.codeWriter = this.classWriter.define(
			public_().static_().final_().method( void.class, MAIN_METHOD ) );
	}
	
	private final void suppressRecording() {
		this.suppressRecording = true;
	}
	
	private final void restoreRecording() {
		this.suppressRecording = false;
	}
	
	private final void printError( final String error ) throws IOException {
		this.consoleReader.printString( error );
		this.consoleReader.printNewline();
	}
	
	private final String readCommand() throws IOException {
		return this.consoleReader.readLine( ">", '\0' );
	}
	
	private static final boolean isExit( final String command ) {
		return EXIT.equals( command );
	}

	private static final Method findMethod( final String command ) {
		for ( Method method : JavaCodeWriter.class.getMethods() ) {
			if ( isMatch ( method, command ) ) {
				return method;
			}
		}
		return null;
	}
	
	private static final boolean isWritingMethod( final Method method ) {
		return method.getDeclaringClass().equals( JavaCodeWriter.class ) &&
			method.getReturnType().equals( JavaCodeWriter.class );
	}
	
	private static final boolean isCoreWritingMethod( final Method method ) {
		return method.getDeclaringClass().equals( JavaCoreCodeWriter.class ) &&
			method.getReturnType().equals( JavaCoreCodeWriter.class );
	}
	
	private static final boolean isMatch(
		final Method method,
		final String command )
	{
		return method.getName().equals( command ) && isWritingMethod( method );
	}
	
	private final class ReplMonitor extends JakMonitor {
		@Override
		public final JavaCoreCodeWriter monitor( final JavaCoreCodeWriter wrappedWriter ) {
			return new WriterDelegate( wrappedWriter ).getProxy();
		}
		
		@Override
		public final Locals monitor( final Locals locals ) {
			//TODO: wrap locals
			return locals;
		}
		
		@Override
		public final Stack monitor( final Stack stack ) {
			// TODO: wrap stack
			return stack;
		}
	}
		
	private final class WriterDelegate extends Delegate< JavaCoreCodeWriter > {
		private final JavaCoreCodeWriter coreWriter;
		
		WriterDelegate( final JavaCoreCodeWriter coreWriter ) {
			super( JavaCoreCodeWriter.class );
			
			this.coreWriter = coreWriter;
			this.replay();
		}
		
		private final void replay() {
			Iterator< Method > methodIter = JakRepl.this.methods.iterator();
			Iterator< Object[] > argsIter = JakRepl.this.args.iterator();

			while ( methodIter.hasNext() ) {
				Method method = methodIter.next();
				Object[] args = argsIter.next();
				
				try {
					method.invoke( this.coreWriter, args );
				} catch ( IllegalArgumentException e ) {
					throw new IllegalStateException( e );
				} catch ( IllegalAccessException e ) {
					throw new IllegalStateException( e );
				} catch ( InvocationTargetException e ) {
					throw new IllegalStateException( e );
				}
			}			
		}
		
		@Override
		protected final void before(
			final Method interfaceMethod,
			final Object[] args )
		{
			if ( isCoreWritingMethod( interfaceMethod ) && ! JakRepl.this.suppressRecording ) {
				this.op( interfaceMethod.getName() );
				for ( Object arg: args ) {
					this.operand( arg );
				}
				this.endl();
			}
		}
		
		@Override
		protected final Object invoke(
			final Method interfaceMethod,
			final Object[] args )
			throws Throwable
		{
			return this.invokeOn( this.coreWriter, interfaceMethod, args );
		}
		
		@Override
		protected final void after(
			final Method interfaceMethod,
			final Object[] args,
			final Object result )
		{
			if ( isCoreWritingMethod( interfaceMethod ) && ! JakRepl.this.suppressRecording ) {
				JakRepl.this.methods.add( interfaceMethod );
				JakRepl.this.args.add( args );
				
				//duplicate the result, retrieve the stack field, swap them, finally call push
				this.coreWriter.dup();
				
				this.coreWriter.getstatic(
					JakRepl.this.classWriter.thisType(),
					JavaAssembler.field( ReplStack.class, STACK_FIELD ) );
				
				this.coreWriter.swap();
				
				this.coreWriter.invokevirtual(
					ReplStack.class,
					JavaAssembler.method( void.class, "push", int.class ) );
			}
		}

		private final WriterDelegate op( final String code ) {
			return this.append( code );
		}
		
		private final WriterDelegate operand( final Object operand ) {
			if ( operand instanceof Byte ) {
				return this.operand( ((Byte)operand).byteValue() );
			} else if ( operand instanceof Short ) {
				return this.operand( ((Short)operand).shortValue() );
			} else if ( operand instanceof Integer ) {
				return this.operand( ((Integer)operand).intValue() );
			} else if ( operand instanceof Type ) {
				return this.operand( (Type)operand );
			} else if ( operand instanceof Jump ) {
				return this.operand( (Jump)operand );
			} else {
				return this.todo( operand.getClass() );
			}
		}
		
		private final WriterDelegate operand( final byte operand ) {
			//TODO: Verify correctness
			return this.append( ' ' ).append( Byte.toString( operand ) );
		}
		
		private final WriterDelegate operand( final short operand ) {
			return this.append( ' ' ).append( Short.toString( operand ) );
		}
		
		private final WriterDelegate operand( final int operand ) {
			return this.append( ' ' ).append( Integer.toString( operand ) );
		}
		
		private final WriterDelegate operand( final Type operand ) {
			Class< ? > operandClass = JavaTypes.getRawClass( operand );
			return this.append( ' ' ).append( operandClass.getCanonicalName() );
		}
		
		private final WriterDelegate operand( final Jump jump ) {
			//TODO: Implement better
			return this.append( ' ' ).append( jump.toString() );
		}
		
		private final WriterDelegate todo( final Class< ? > aClass ) {
			//TODO: Replace this for field and method signatures
			return this.append( " TODO " + aClass.getSimpleName() );
		}
		
		private final WriterDelegate endl() {
			//TODO: Platform specific new line
			try {
				JakRepl.this.consoleReader.printNewline();
				JakRepl.this.consoleReader.flushConsole();
				return this;
			} catch ( IOException e ) {
				throw new IllegalStateException( e );
			}
		}
		
		private final WriterDelegate append( final char ch ) {
			try {
				JakRepl.this.consoleReader.printString( Character.toString( ch ) );
			} catch ( IOException e ) {
				throw new IllegalStateException( e );
			}
			return this;
		}
		
		private final WriterDelegate append( final String str ) {
			try {
				JakRepl.this.consoleReader.printString( str );
			} catch ( IOException e ) {
				throw new IllegalStateException( e );
			}
			return this;
		}
	}
}
