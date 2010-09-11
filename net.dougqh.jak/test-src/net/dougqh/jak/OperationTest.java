package net.dougqh.jak;

import static net.dougqh.jak.JavaAssembler.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.dougqh.jak.annotations.Op;
import net.dougqh.jak.operations.Operation;
import net.dougqh.jak.operations.Operations;

public final class OperationTest extends TestCase {
	public static final TestSuite suite() {
		TestSuite suite = new TestSuite();
		for ( Method method : JavaCoreCodeWriter.class.getDeclaredMethods() ) {
			Operation testOperation = createTestOperation( method );
			if ( ( testOperation != null ) && ! testOperation.isPolymorphic() ) {
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
		
		ExpectationMonitor monitor = new ExpectationMonitor( this.operation, this.method );
		typeWriter.monitor( monitor );
		
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
		
		monitor.assertDone();
	}
	
	private static final class ExpectationMonitor extends JakMonitor {
		private final Operation operation;
		private final Method expectedMethod;
		
		private CheckedStack checkedStack;
		
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
			this.checkedStack = new CheckedStack( this.operation, stack );
			return this.checkedStack;
		}
		
		final void assertDone() {
			this.checkedStack.assertDone();
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
	
	private static abstract class TestStack implements Stack {
		protected final Stack stack;
		
		TestStack( final Stack stack ) {
			this.stack = stack;
		}
		
		@Override
		public void push( final Type type ) {
			fail();
		}
		
		@Override
		public void pop( final Type type ) {
			fail();
		}
		
		@Override
		public void pop() {
			fail();
		}
		
		@Override
		public void pop2() {
			fail();
		}
		
		@Override
		public void swap() {
			fail();
		}
		
		@Override
		public void dup() {
			fail();
		}
		
		@Override
		public void dup_x1() {
			fail();
		}
		
		@Override
		public void dup2() {
			fail();
		}
		
		@Override
		public void dup2_x1() {
			fail();
		}
		
		@Override
		public void dup2_x2() {
			fail();
		}
		
		@Override
		public void dup_x2() {
			fail();
		}
		
		@Override
		public final int maxStack() {
			return this.stack.maxStack();
		}
	}
	
	private static final class CheckedStack extends TestStack {
		private final ListIterator< Class< ? > > operandIter;
		private final ListIterator< Class< ? > > resultIter;
		
		CheckedStack( final Operation operation, final Stack stack ) {
			super( stack );
			
			List< Class< ? > > operandTypes = Arrays.asList( operation.getStackOperandTypes() );
			this.operandIter = operandTypes.listIterator( operandTypes.size() );
			
			List< Class< ? > > resultTypes = Arrays.asList( operation.getStackResultTypes() );
			this.resultIter = resultTypes.listIterator();
		}
		
		@Override
		public final void pop( final Type type ) {
			Type expectedType = this.operandIter.previous();
			assertEquals( expectedType, type );
			
			this.stack.pop( type );
		}
		
		@Override
		public final void push( final Type type ) {
			Type expectedType = this.resultIter.next();
			assertEquals( expectedType, type );
			
			this.stack.push( type );
		}
		
		final void assertDone() {
			assertFalse( "Mismatched stack operands", this.operandIter.hasPrevious() );
			assertFalse( "Mismatched stack results", this.resultIter.hasNext() );
		}
	}
}
