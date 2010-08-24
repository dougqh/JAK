package net.dougqh.jak.api;

import static net.dougqh.jak.JavaAssembler.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.dougqh.jak.JavaClassWriter;

import org.junit.Test;

public final class CodeGenSynchronizationTest {
	public final @Test void synchronization() {
		JavaClassWriter classWriter = define(
			public_().final_().class_( "WaitImpl" ).implements_( Wait.class ) );
		
		classWriter.defineDefaultConstructor();
		
		classWriter.define(
			public_().final_().method( void.class, "waitOn", Object.class ) ).
			aload_1().
			monitorenter().
			label( "try" ).
			aload_1().
			invokevirtual( Object.class, method( void.class, "wait" ) ).
			label( "endTry" ).
			aload_1().
			monitorexit().
			return_().
			catch_( "try", "endTry", Throwable.class ).
			astore( "e" ).
			aload_1().
			monitorexit().
			aload( "e" ).
			athrow();
		
		final Wait wait = classWriter.< Wait >newInstance();
		
		final Object target = new Object();
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future< Void > future = executor.submit(
			new Callable< Void >() {
				@Override
				public final Void call() throws Exception {
					wait.waitOn( target );
					return null;
				}
			} );
		
		while ( ! future.isDone() ) {
			synchronized ( target ) {
				target.notify();
			}
		}
	}
	
	public static interface Wait {
		public abstract void waitOn( final Object object )
			throws InterruptedException;
	}
}
