package net.dougqh.jak.repl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import net.dougqh.jak.JavaCoreCodeWriter;

final class ReplRecorder {
	private final ArrayList< Method > methods = new ArrayList< Method >( 32 );
	private final ArrayList< Object[] > args = new ArrayList< Object[] >( 32 );
	
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
	
	final void replay( final JavaCoreCodeWriter coreWriter ) {
		Iterator< Method > methodIter = this.methods.iterator();
		Iterator< Object[] > argsIter = this.args.iterator();

		while ( methodIter.hasNext() ) {
			Method method = methodIter.next();
			Object[] args = argsIter.next();
			
			try {
				method.invoke( coreWriter, args );
			} catch ( IllegalArgumentException e ) {
				throw new IllegalStateException( e );
			} catch ( IllegalAccessException e ) {
				throw new IllegalStateException( e );
			} catch ( InvocationTargetException e ) {
				throw new IllegalStateException( e );
			}
		}
	}
	
	final void list( final ReplConsole console ) {
		Iterator< Method > methodIter = this.methods.iterator();
		Iterator< Object[] > argsIter = this.args.iterator();
		
		while ( methodIter.hasNext() ) {
			Method method = methodIter.next();
			Object[] args = argsIter.next();
			
			if ( args.length != 0 ) {
				console.append( method.getName() ).todo().endl();
			} else {
				console.append( method.getName() ).endl();
			}
		}
	}
}
