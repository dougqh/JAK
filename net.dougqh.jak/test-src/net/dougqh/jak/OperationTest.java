package net.dougqh.jak;

import static net.dougqh.jak.JavaAssembler.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.Iterator;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.dougqh.jak.annotations.Op;
import net.dougqh.jak.operations.Operation;
import net.dougqh.jak.operations.Operations;
import net.dougqh.jak.types.Types;

public final class OperationTest extends TestCase {
	public static final TestSuite suite() {
		TestSuite suite = new TestSuite();
		for ( Method method : JavaCoreCodeWriter.class.getDeclaredMethods() ) {
			Operation testOperation = createTestOperation( method );
			if ( ( testOperation != null ) && isFullyDefined( testOperation ) ) {
				suite.addTest( new OperationTest( testOperation, method ) );
			}
		}
		return suite;
	}
	
	private static final Operation createTestOperation( final Method method ) {
		Op opAnnotation = method.getAnnotation( Op.class );
		if ( opAnnotation == null ) {
			return null;
		} else {
			return Operations.getPrototype( opAnnotation.value() );
		}
	}
	
	private static final boolean isFullyDefined( final Operation operation ) {
		return areFullyDefined( operation.getStackOperandTypes() ) &&
			areFullyDefined( operation.getStackResultTypes() );
	}
	
	private static final boolean areFullyDefined( final Class< ? >... classes ) {
		for ( Class< ? > aClass : classes ) {
			//TODO: Revisit handling of branch operations
			if ( Types.isPlaceholderClass( aClass ) ) {
				return false;
			}
		}
		return true;
	}

	private final Operation operation;
	private final Method method;
	
	OperationTest(
		final Operation operation,
		final Method method )
	{
		super( operation.getId() );
		
		this.operation = operation;
		this.method = method;
	}
	
	@Override
	protected final void runTest() throws Throwable {
		TypeWriter typeWriter = new TypeWriter(
			new TypeWriterGroup(),
			class_( "Test" ).typeDescriptor(),
			0 );
		
		typeWriter.monitor( new ExpectationMonitor( this.operation, this.method ) );
		
		JavaCoreCodeWriter coreWriter = typeWriter.define(
			static_().method( void.class, "main" ),
			0,
			null );
		
		try {
			this.operation.write( coreWriter );
		} catch ( UndeclaredThrowableException e ) {
			if ( e.getCause() instanceof AssertionFailedError ) {
				throw (AssertionFailedError)e.getCause();
			} else {
				throw e;
			}
		}
	}
	
	private static final class ExpectationMonitor extends JakMonitor {
		private final Operation operation;
		private final Method expectedMethod;
		
		ExpectationMonitor(
			final Operation operation,
			final Method method )
		{
			this.operation = operation;
			this.expectedMethod = method;
		}
		
		@Override
		public final JavaCoreCodeWriter monitor( final JavaCoreCodeWriter wrappedWriter ) {
			return (JavaCoreCodeWriter)Proxy.newProxyInstance(
				this.getClass().getClassLoader(),
				new Class< ? >[] { JavaCoreCodeWriter.class },
				new CheckedInvocationHandler( wrappedWriter, this.expectedMethod ) );
		}
		
		@Override
		public final Stack monitor( final Stack stack ) {
			return new CheckedStack( this.operation, stack );
		}
	}
	
	private static final class CheckedInvocationHandler implements InvocationHandler {
		private final JavaCoreCodeWriter wrappedWriter;
		private final Method expectedMethod;
		
		private CheckedInvocationHandler(
			final JavaCoreCodeWriter wrappedWriter,
			final Method expectedMethod )
		{
			this.wrappedWriter = wrappedWriter;
			this.expectedMethod = expectedMethod;
		}
		
		@Override
		public final Object invoke(
			final Object proxy,
			final Method method,
			final Object[] args )
			throws Throwable
		{
			assertEquals( this.expectedMethod, method );
			
			return method.invoke( this.wrappedWriter, args );
		}
	}
	
	private static final class CheckedStack implements Stack {
		private final Iterator< Class< ? > > operandIter;
		private final Iterator< Class< ? > > resultIter;
		
		private final Stack stack;
		
		CheckedStack( final Operation operation, final Stack stack ) {
			this.operandIter = Arrays.asList( operation.getStackOperandTypes() ).iterator();
			this.resultIter = Arrays.asList( operation.getStackResultTypes() ).iterator();
			
			this.stack = stack;
		}
		
		@Override
		public final void pop( final Type type ) {
			//TODO: Flip this to remove last - and then fix the 
			//order of the stack calls in JavaCoreCodeWriter
			Type expectedType = this.operandIter.next();
			assertEquals( expectedType, type );
			
			this.stack.pop( type );
		}
		
		@Override
		public final void push( final Type type ) {
			Type expectedType = this.resultIter.next();
			assertEquals( expectedType, type );
			
			this.stack.push( type );
		}
		
		@Override
		public final int maxStack() {
			return this.stack.maxStack();
		}
	}
}
