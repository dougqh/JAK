package net.dougqh.jak.repl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import jline.CandidateListCompletionHandler;
import jline.Completor;
import jline.ConsoleReader;
import jline.ConsoleReaderInputStream;
import jline.History;
import net.dougqh.jak.annotations.Op;
import net.dougqh.jak.operations.Operation;
import net.dougqh.jak.operations.Operations;

final class ReplConsole {
	private final ConsoleReader reader;
	
	ReplConsole() throws IOException {
		File tempFile = File.createTempFile( "JavaAssemblerRepl", ".history" );

		this.reader = new ConsoleReader();
		this.reader.setHistory( new History( tempFile ) );
		this.reader.setCompletionHandler( new CandidateListCompletionHandler() );
	}
	
	final ReplConsole addCompletor( final Completor completor ) {
		this.reader.addCompletor( completor );
		return this;
	}
	
	final ReplConsole install() {
		ConsoleReaderInputStream.setIn( this.reader );
		return this;
	}
	
	final ReplConsole uninstall() {
		ConsoleReaderInputStream.restoreIn();
		return this;
	}
	
	final void complete( final String buffer ) throws IOException {
		this.reader.getCursorBuffer().clearBuffer();
		this.reader.getCursorBuffer().write( buffer );
		try {
			Method completeMethod = ConsoleReader.class.getDeclaredMethod( "complete" );
			completeMethod.setAccessible( true );
			completeMethod.invoke( this.reader );
		} catch ( NoSuchMethodException e ) {
			throw new IllegalStateException( e );
		} catch ( SecurityException e ) {
			throw new IllegalStateException( e );
		} catch ( IllegalArgumentException e ) {
			throw new IllegalStateException( e );
		} catch ( IllegalAccessException e ) {
			throw new IllegalStateException( e );
		} catch ( InvocationTargetException e ) {
			throw new IllegalStateException( e );
		} finally {
			this.reader.getCursorBuffer().clearBuffer();
		}
	}

	final ReplConsole clear() {
		try {
			//DQH - 10-10-2010 - Not using ConsoleReader.clearScreen 
			//because it does not work as desired when the REPL is run 
			//inside of Eclipse.
			for ( int i = 0; i < 40; ++i ) {
				this.reader.printNewline();
			}
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
		return this;
	}
	
	final String readCommand() throws IOException {
		return this.reader.readLine( "=>", '\0' );
	}
	
	final ReplConsole printUsage( final Iterable< ReplMethod > methods ) {
		for ( ReplMethod method : methods ) {
			this.printUsage( method );
		}
		return this;
	}
	
	final ReplConsole printUsage( final ReplMethod method ) {
		this.append( "Usage: " );
		this.append( method.getName() );
		for ( ReplArgument argument : method.getArguments() ) {
			this.append( ' ' );
			this.append( argument.getTypeName() ); 
		}
		this.endl();
		return this;
	}
	
	final ReplConsole printUsage( final String command, final Class< ? >... argTypes ) {
		this.append( "Usage: " );
		this.append( command );
		for ( Class< ? > argType : argTypes ) {
			this.append( ' ' );
			this.append( ReplFormatter.getDisplayName( argType ) );
		}
		this.endl();
		return this;
	}
	
	final ReplConsole print( final Method interfaceMethod, final Object[] args ) {
		this.op( getNameOf( interfaceMethod ) );
		for ( Object arg: args ) {
			this.operand( arg );
		}
		this.endl();
		
		return this;
	}
	
	final ReplConsole printColumns( final String... columns ) {
		try {
			this.reader.printColumns( Arrays.asList( columns ) );
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
		return this;
	}
	
	final void printNotice( final String notice ) throws IOException {
		this.println( notice );
	}
	
	final void printError( final String error ) throws IOException {
		this.println( error );
	}
	
	final void println() throws IOException {
		this.reader.printNewline();
	}
	
	final void println( final String string ) throws IOException {
		this.reader.printString( string );
		this.reader.printNewline();
	}

	final ReplConsole op( final String code ) {
		return this.append( code );
	}
	
	private final ReplConsole operand( final Object operand ) {
		return this.append( ' ' ).append( ReplFormatter.format( operand ) );
	}
	
	final ReplConsole append( final char ch ) {
		try {
			this.reader.printString( Character.toString( ch ) );
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
		return this;
	}
	
	final ReplConsole append( final ReplEnum< ? > replEnum ) {
		return this.append( replEnum.toString() );
	}
	
	final ReplConsole append( final String str ) {
		try {
			this.reader.printString( str );
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
		return this;
	}
	
	final ReplConsole endl() {
		//TODO: Platform specific new line
		try {
			this.reader.printNewline();
			this.reader.flushConsole();
			return this;
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
	}
	
	private static final String getNameOf( final Method method ) {
		Op op = method.getAnnotation( Op.class );
		Operation operation = Operations.getPrototype( op.value() );
		return operation.getId();
	}
}
