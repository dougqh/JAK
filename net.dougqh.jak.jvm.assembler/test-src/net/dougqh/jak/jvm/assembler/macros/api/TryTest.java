package net.dougqh.jak.jvm.assembler.macros.api;

import static net.dougqh.jak.Jak.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import net.dougqh.jak.jvm.assembler.JvmClassWriter;
import net.dougqh.jak.jvm.assembler.JvmCodeWriter;
import net.dougqh.jak.jvm.assembler.JvmWriter;
import net.dougqh.jak.jvm.assembler.macros.stmt;

import org.junit.Test;

public final class TryTest {
	private TryFixture tryFinally() {
		return tryCatchFinally();
	}
	
	private TryFixture tryCatchFinally( final Class<? extends Exception>... exceptions ) {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "TryCatchFinally" ).extends_( TryFixture.class ) );
		
		classWriter.defineDefaultConstructor();
	
		JvmCodeWriter codeWriter = classWriter.define(
			method( int_, "exec", Function.class, "func" ).throws_( Throwable.class ) );
		
		codeWriter.try_( new stmt() {
			protected final void body() {
				aload( "func" ).
				invokeinterface( Function.class, method( int_, "eval" ) ).
				ireturn();
			}
		} );
		
		for ( Class<? extends Exception> exception: exceptions ) {
			codeWriter.catch_( exception, "e", new stmt() {
				@Override
				protected final void body() {
					this_().
					aload( "e" ).
					invokevirtual( Object.class, Class.class, "getClass" ).
					putfield( thisType(), Class.class, "caughtType" );
				}
			} );
		}
		
		codeWriter.finally_( new stmt() {
			protected final void body() {
				this_().
				true_().
				putfield( thisType(), field( boolean_, "finishedFinally" ) );
			}
		} );
		
		codeWriter.ireturn( -1 );
		
		return classWriter.< TryFixture >newInstance();
	}
	
	private TryFixture tryCatch( final Class<? extends Exception>... exceptions ) {
		JvmClassWriter classWriter = new JvmWriter().define(
			public_().class_( "TryCatch" ).extends_( TryFixture.class ) );
		
		classWriter.defineDefaultConstructor();
	
		JvmCodeWriter codeWriter = classWriter.define(
			method( int_, "exec", Function.class, "func" ).throws_( Throwable.class ) );
		
		codeWriter.try_( new stmt() {
			protected final void body() {
				aload( "func" ).
				invokeinterface( Function.class, method( int_, "eval" ) ).
				ireturn();
			}
		} );
		
		for ( Class<? extends Exception> exception: exceptions ) {
			codeWriter.catch_( exception, "e", new stmt() {
				@Override
				protected final void body() {
					this_().
					aload( "e" ).
					invokevirtual( Object.class, Class.class, "getClass" ).
					putfield( thisType(), Class.class, "caughtType" );
				}
			} );
		}
		
		codeWriter.ireturn( -1 );
		
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
	public final void try_finally_fail_checked() throws Throwable {
		TryFixture tryFinally = tryFinally();
		
		try {
			tryFinally.exec( new Function() {
				@Override
				public final int eval() throws Throwable {
					throw new CheckedException1();
				}
			} );
			
			fail();
		} catch ( CheckedException1 e ) {
			//pass
		}
		
		assertThat( tryFinally.finishedFinally, is( true ) );
	}

	@Test
	public final void try_finally_fail_unchecked() throws Throwable {
		TryFixture tryFinally = tryFinally();
		
		try {
			tryFinally.exec( new Function() {
				@Override
				public final int eval() throws Throwable {
					throw new UnsupportedOperationException();
				}
			} );
			
			fail();
		} catch ( UnsupportedOperationException e ) {
			//pass
		}
		
		assertThat( tryFinally.finishedFinally, is( true ) );
	}
	
	@Test
	public final void try_catch_finally() throws Throwable {
		TryFixture tryCatchFinally = tryCatchFinally(
			CheckedException1.class,
			CheckedException2.class,
			CheckedException3.class );

		tryCatchFinally.exec( new Function() {
			@Override
			public final int eval() throws Throwable {
				throw new CheckedException1();
			}
		} );
		
		assertThat( tryCatchFinally.caughtType, is( (Object)CheckedException1.class ) );
		assertThat( tryCatchFinally.finishedFinally, is( true ) );		
	}
	
	@Test
	public final void try_catch() throws Throwable {
		TryFixture tryCatch = tryCatch(
			CheckedException1.class,
			CheckedException2.class,
			CheckedException3.class );

		tryCatch.exec( new Function() {
			@Override
			public final int eval() throws Throwable {
				throw new CheckedException2();
			}
		} );
		
		assertThat( tryCatch.caughtType, is( (Object)CheckedException2.class ) );
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
	
	public static class CheckedException1 extends Exception {
		private static final long serialVersionUID = 1L;
	}
	
	public static class CheckedException2 extends Exception {
		private static final long serialVersionUID = 2L;
	}
	
	public static final class CheckedException3 extends Exception {
		private static final long serialVersionUID = 3L;
	}
}
