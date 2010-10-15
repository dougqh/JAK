package net.dougqh.jak.api;

import static net.dougqh.jak.JavaAssembler.*;
import static org.junit.Assert.*;

import java.net.MalformedURLException;

import net.dougqh.jak.JavaClassWriter;

import org.junit.Test;

public final class ExceptionsTest {
	@Test( expected=TestException.class ) 
	public final void throwsException() throws Exception {
		JavaClassWriter writer = define(
			public_().final_().class_( "Thrower" ).implements_( Thrower.class ) );
		
		writer.define( public_().init() ).
			this_().
			invokespecial( Object.class, init() ).
			return_();
		
		writer.define( public_().method( void.class, "throwIt" ).throws_( Exception.class ) ).
			new_( TestException.class ).
			dup().
			invokespecial( TestException.class, init() ).
			athrow();
		
		Thrower thrower = writer.< Thrower >newInstance();
		thrower.throwIt();
	}
	
	@Test( expected=TestRuntimeException.class )
	public final void throwsRuntimeException() throws Exception {
		JavaClassWriter writer = define(
			public_().final_().class_( "Throws" ).implements_( Thrower.class ) );
		
		writer.define( public_().init() ).
			this_().
			invokespecial( Object.class, init() ).
			return_();
		
		writer.define( public_().method( void.class, "throwIt" ) ).
			new_( TestRuntimeException.class ).
			dup().
			invokespecial( TestRuntimeException.class, init() ).
			athrow();
		
		Thrower thrower = writer.< Thrower >newInstance();
		thrower.throwIt();
	}
	
	public final @Test void exceptionHandling() {
		JavaClassWriter writer = define(
			public_().final_().class_( "ExceptionHandling" ).implements_( Handler.class ) );
		
		writer.define( public_().init() ).
			this_().
			invokespecial( Object.class, init() ).
			return_();
		
		writer.define( public_().method( String.class, "handle", Thrower.class ) ).
			label( "try" ).
			aload_1().
			invokeinterface( Thrower.class, void.class, "throwIt" ).
			aconst_null().
			areturn().
			label( "endTry" ).
			
			catch_( "try", "endTry", MalformedURLException.class ).
			pop().
			ldc( "Bad URL" ).
			areturn().
			
			catch_( "try", "endTry", NullPointerException.class ).
			pop().
			ldc( "Null" ).
			areturn().
			
			catch_( "try", "endTry", Exception.class ).
			pop().
			ldc( "Other" ).
			areturn();
		
		Handler handler = writer.< Handler >newInstance();
		
		assertNull( handler.handle( new NoExceptionThrower() ) );
		assertEquals( "Bad URL", handler.handle( new MalformedUrlExceptionThrower() ) );
		assertEquals( "Null", handler.handle( new NullPointerExceptionThrower() ) );
	}
	
	public final @Test void exceptionHandlingWithCatchLabel() {
		JavaClassWriter writer = define(
			public_().final_().class_( "ExceptionHandlingWithCatchLabels" ).implements_( Handler.class ) );
		
		writer.define( public_().init() ).
			this_().
			invokespecial( Object.class, init() ).
			return_();
		
		writer.define( public_().method( String.class, "handle", Thrower.class ) ).
			label( "try" ).
			aload_1().
			invokeinterface( Thrower.class, void.class, "throwIt" ).
			aconst_null().
			areturn().
			label( "endTry" ).
			
			label( "bad-url" ).
			pop().
			ldc( "Bad URL" ).
			areturn().
			
			label( "npe" ).
			pop().
			ldc( "Null" ).
			areturn().
			
			label( "other" ).
			pop().
			ldc( "Other" ).
			areturn().
			
			exception( "try", "endTry", MalformedURLException.class, "bad-url" ).
			exception( "try", "endTry", NullPointerException.class, "npe" ).
			exception( "try", "endTry", Exception.class, "other" );
		
		Handler handler = writer.< Handler >newInstance();
		
		assertNull( handler.handle( new NoExceptionThrower() ) );
		assertEquals( "Bad URL", handler.handle( new MalformedUrlExceptionThrower() ) );
		assertEquals( "Null", handler.handle( new NullPointerExceptionThrower() ) );
	}
	
	public static interface Thrower {
		public abstract void throwIt() throws Exception;
	}
	
	public static interface Handler {
		public abstract String handle( final Thrower thrower );
	}
	
	public static final class NoExceptionThrower implements Thrower {
		@Override
		public final void throwIt() {}
	}
	
	public static final class MalformedUrlExceptionThrower implements Thrower {
		@Override
		public final void throwIt() throws Exception {
			throw new MalformedURLException();
		}
	}
	
	public static final class NullPointerExceptionThrower implements Thrower {
		@Override
		public final void throwIt() {
			throw new NullPointerException();
		}
	}
	
	public static final class TestException extends Exception {
		private static final long serialVersionUID = 9144689697619057094L;
	}
	
	public static final class TestRuntimeException extends Exception {
		private static final long serialVersionUID = 8495868059744044354L;
	}
}
