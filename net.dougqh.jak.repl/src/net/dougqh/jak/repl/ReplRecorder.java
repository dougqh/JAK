package net.dougqh.jak.repl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

import net.dougqh.jak.assembler.ConstantEntry;
import net.dougqh.jak.assembler.JavaCodeWriter;
import net.dougqh.jak.assembler.JavaTypeWriter;

final class ReplRecorder {
	private ArrayList< Method > methods = new ArrayList< Method >( 32 );
	private ArrayList< Object[] > args = new ArrayList< Object[] >( 32 );
	
	private ArrayList< Method > checkpointMethods;
	private ArrayList< Object[] > checkpointArgs;
	
	ReplRecorder() {
		this.checkpoint();
	}
	
	final void checkpoint() {
		this.checkpointMethods = new ArrayList< Method >( this.methods );
		this.checkpointArgs = new ArrayList< Object[] >( this.args );
	}
	
	final void rollback() {
		this.methods = checkpointMethods;
		this.args = checkpointArgs;
	}
	
	final int size() {
		return this.methods.size();
	}
	
	final void reset() {
		this.methods.clear();
		this.args.clear();
	}
	
	final void record( final Method method, final Object[] args ) {
		this.methods.add( method );
		this.args.add( args );
	}
	
	final void replay(
		final JavaTypeWriter typeWriter,
		final JavaCodeWriter codeWriter )
	{
		Iterator< Method > methodIter = this.methods.iterator();
		Iterator< Object[] > argsIter = this.args.iterator();

		while ( methodIter.hasNext() ) {
			Method method = methodIter.next();
			Object[] args = argsIter.next();
			
			if ( isLdc( method ) ) {
				ldc( codeWriter, args );
			} else {
				invoke( codeWriter, method, args );
			}
		}
	}
	
	private static final boolean isLdc( final Method method ) {
		return method.getName().equals( "ldc" ) ||
			method.getName().equals( "ldc_w" ) ||
			method.getName().equals( "ldc2_w" );
	}
	
	private static final void ldc(
		final JavaCodeWriter codeWriter,
		final Object... args )
	{
		ConstantEntry entry = (ConstantEntry)args[ 0 ];
		Type type = entry.type();
		Object value = entry.value();
		
		if ( type.equals( int.class ) ) {
			codeWriter.ldc( (Integer)value );
		} else if ( type.equals( float.class ) ) {
			codeWriter.ldc( (Float)value );
		} else if ( type.equals( long.class ) ) {
			codeWriter.ldc2_w( (Long)value );
		} else if ( type.equals( double.class ) ) {
			codeWriter.ldc2_w( (Double)value );
		} else if ( type.equals( String.class ) ) {
			codeWriter.ldc( (CharSequence)value );
		} else if ( type.equals( Class.class ) ) {
			codeWriter.ldc( (Class< ? >)value );
		} else {
			throw new IllegalStateException();
		}
	}
	
	private static final void invoke(
		final JavaCodeWriter codeWriter,
		final Method method,
		final Object... args )
	{
		try {
			method.invoke( codeWriter.coreWriter(), args );
		} catch ( IllegalArgumentException e ) {
			throw new IllegalStateException( e );
		} catch ( IllegalAccessException e ) {
			throw new IllegalStateException( e );
		} catch ( InvocationTargetException e ) {
			throw new IllegalStateException( e );
		}	
	}
	
	final void list( final ReplConsole console ) {
		Iterator< Method > methodIter = this.methods.iterator();
		Iterator< Object[] > argsIter = this.args.iterator();
		
		while ( methodIter.hasNext() ) {
			Method method = methodIter.next();
			Object[] args = argsIter.next();
			
			if ( args.length != 0 ) {
				console.print( method, args );
			} else {
				console.append( method.getName() ).endl();
			}
		}
	}
}
