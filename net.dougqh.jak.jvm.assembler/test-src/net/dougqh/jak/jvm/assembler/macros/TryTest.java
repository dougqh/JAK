package net.dougqh.jak.jvm.assembler.macros;

import static net.dougqh.jak.Jak.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;

import org.junit.Test;

public final class TryTest {
	private TryFixture tryFinally() {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "TryFinally" ).extends_( TryFixture.class ) );
		
		classWriter.defineDefaultConstructor();
	
		classWriter.define(
			method( int_, "exec", Function.class, "func" ).throws_( Throwable.class ) ).
			try_( new stmt() {
				protected final void body() {
					aload( "func" ).
					invokeinterface( Function.class, method( int_, "eval" ) ).
					ireturn();
				}
			} ).finally_( new stmt() {
				protected final void body() {
					this_().
					true_().
					putfield( thisType(), field( boolean_, "finishedFinally" ) );
				}
			} );
		
		return classWriter.< TryFixture >newInstance();
	}
	
	@Test
	public final void try_finally_happy_day() throws Throwable {
		TryFixture tryFinally = tryFinally();
		
		int result = tryFinally.exec( new Function() {
			@Override
			public final int eval() throws Throwable {
				return 1234;
			}
		} );
		
		assertThat( result, is( 1234 ) );
		assertThat( tryFinally.finishedFinally, is( true ) );
	}
	
	@Test
	public final void try_finally_fail() throws Throwable {
		TryFixture tryFinally = tryFinally();
		
		try {
			tryFinally.exec( new Function() {
				@Override
				public final int eval() throws Throwable {
					throw new CheckedException();
				}
			} );
			
			fail();
		} catch ( CheckedException e ) {
			//pass
		}
		
		assertThat( tryFinally.finishedFinally, is( true ) );
	}
	
	public static abstract class TryFixture {
		public boolean finishedTry = false;
		public Class<?> caughtType = null;
		public boolean finishedFinally = false;
	
		public abstract int exec( final Function func ) throws Throwable;
	}
	
	public interface Function {
		public int eval() throws Throwable;
	}
	
	public static class CheckedException extends Exception {
		private static final long serialVersionUID = 1L;
	}
}
