package net.dougqh.jak.repl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import net.dougqh.jak.JavaCoreCodeWriter.Jump;
import net.dougqh.java.meta.types.JavaTypes;

import jline.ConsoleReader;
import jline.ConsoleReaderInputStream;
import jline.History;

final class ReplConsole {
	private final ConsoleReader reader;
	
	ReplConsole() throws IOException {
		File tempFile = File.createTempFile( "JavaAssemblerRepl", ".history" );

		this.reader = new ConsoleReader();
		this.reader.setHistory( new History( tempFile ) );
	}
	
	final void install() {
		ConsoleReaderInputStream.setIn( this.reader );
	}
	
	final void uninstall() {
		ConsoleReaderInputStream.restoreIn();
	}
	
	final void printError( final String error ) throws IOException {
		this.reader.printString( error );
		this.reader.printNewline();
	}
	
	final String readCommand() throws IOException {
		return this.reader.readLine( ">", '\0' );
	}
	
	final ReplConsole print( final Method interfaceMethod, final Object[] args ) {
		this.op( interfaceMethod.getName() );
		for ( Object arg: args ) {
			this.operand( arg );
		}
		this.endl();
		
		return this;
	}

	final ReplConsole op( final String code ) {
		return this.append( code );
	}
	
	private final ReplConsole operand( final Object operand ) {
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
	
	final ReplConsole append( final char ch ) {
		try {
			this.reader.printString( Character.toString( ch ) );
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
		return this;
	}
	
	final ReplConsole append( final String str ) {
		try {
			this.reader.printString( str );
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
		return this;
	}
	
	final ReplConsole operand( final byte operand ) {
		//TODO: Verify correctness
		return this.append( ' ' ).append( Byte.toString( operand ) );
	}
	
	final ReplConsole operand( final short operand ) {
		return this.append( ' ' ).append( Short.toString( operand ) );
	}
	
	final ReplConsole operand( final int operand ) {
		return this.append( ' ' ).append( Integer.toString( operand ) );
	}
	
	final ReplConsole operand( final Type operand ) {
		return this.append( ' ' ).append( JavaTypes.getRawClassName( operand ) );
	}
	
	final ReplConsole operand( final Jump jump ) {
		//TODO: Implement better
		return this.append( ' ' ).append( jump.toString() );
	}
	
	final ReplConsole todo( final Class< ? > aClass ) {
		//TODO: Replace this for field and method signatures
		this.append( " TODO " + aClass.getSimpleName() );
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
}
