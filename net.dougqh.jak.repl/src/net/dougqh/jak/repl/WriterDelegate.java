package net.dougqh.jak.repl;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;

import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.jvm.JvmOperationProcessor;
import net.dougqh.jak.jvm.assembler.JvmCoreCodeWriter;
import net.dougqh.reflection.Delegate;

final class WriterDelegate extends Delegate< JvmCoreCodeWriter > {	
	private final JakRepl repl;
	private final JvmCoreCodeWriter coreWriter;
	
	private MethodBuffer buffer = null;

	WriterDelegate(
		final JakRepl repl,
		final JvmCoreCodeWriter coreWriter )
	{
		super( JvmCoreCodeWriter.class );
		
		this.repl = repl;
		this.coreWriter = coreWriter;
	}
	
	@Override
	protected final void before(
		final Method interfaceMethod,
		final Object[] args )
	{
		if ( isCoreWritingMethod( interfaceMethod ) && this.repl.isRecordingEnabled() ) {
			if ( this.repl.config().echo() ) {
				this.repl.console().print( interfaceMethod, args );
			}
			this.repl.recorder().record( interfaceMethod, args );
		}
	}
	
	@Override
	protected final Object invoke(
		final Method interfaceMethod,
		final Object[] args )
		throws Throwable
	{
		if ( isCoreWritingMethod( interfaceMethod ) ) {
			if ( is( interfaceMethod, "new_" ) ) {
				return this.startBuffering( new NewMethodBuffer( interfaceMethod, args ) );
			} else {
				return this.invoke( new MethodInvocation( interfaceMethod, args ) );
			}
		} else {
			if ( is( interfaceMethod, "finish" ) ) {
				this.finish();
				return null;
			} else {
				return this.invokeOn( this.coreWriter, interfaceMethod, args );
			}
		}
	}
	
	private static final boolean is( final Method method, final String name ) {
		return method.getName().equals( name );
	}
	
	private final void finish() {
		if ( this.buffer != null ) {
			MethodBuffer oldBuffer = this.buffer;
			this.buffer = null;
			
			try {
				oldBuffer.writeIncomplete();
			} catch ( Throwable e ) {
				throw new IllegalStateException( e );
			}
		}
		this.coreWriter.finish();
	}
	
	private final JvmCoreCodeWriter startBuffering( final MethodBuffer buffer ) {
		this.buffer = buffer;
		return this.getProxy();
	}
	
	private final JvmCoreCodeWriter invoke( final MethodInvocation invocation )
		throws Throwable
	{
		if ( this.buffer != null ) {
			this.buffer.add( invocation );
			
			if ( this.buffer.isTerminator( invocation ) ) {
				MethodBuffer oldBuffer = this.buffer;
				this.buffer = null;
				oldBuffer.writeNormally();
			}
			return this.getProxy();
		} else {
			this.invokeOn( this.coreWriter, invocation.method, invocation.args );
			return this.getProxy();
		}
	}
	
	private static final boolean isCoreWritingMethod( final Method method ) {
		return method.getDeclaringClass().equals( JvmOperationProcessor.class );
	}
	
	private abstract class MethodBuffer {
		private final MethodInvocation startingInvocation;
		
		final ArrayList< MethodInvocation > followingInvocations = 
			new ArrayList< MethodInvocation >( 4 );
		
		MethodBuffer( final Method method, final Object[] args ) {
			this.startingInvocation = new MethodInvocation( method, args );
		}

		abstract boolean isTerminator( final MethodInvocation invocation );	
		
		final < T > T arg( final int index ) {
			return this.startingInvocation.< T >arg( index );
		}
		
		final void add( final MethodInvocation invocation ) {
			this.followingInvocations.add( invocation );
		}
		
		final void writeNormally() throws Throwable {
			this.startWriteNormally();
			try {
				this.startingInvocation.write();
				for ( MethodInvocation invocation : this.followingInvocations ) {
					invocation.write();
				}
			} finally {
				this.endWriteNormally();
			}
		}
		
		void startWriteNormally() {
		}
		
		void endWriteNormally() {
		}
		
		final void writeIncomplete() throws Throwable {
			this.startWriteIncomplete();
			try {
				this.writePlaceholder();
				for ( MethodInvocation invocation : this.followingInvocations ) {
					invocation.write();
				}
			} finally {
				this.endWriteIncomplete();
			}
		}
		
		void startWriteIncomplete() {
		}
		
		abstract void writePlaceholder() throws Throwable;
		
		void endWriteIncomplete() {
		}
	}
	
	private class MethodInvocation {
		final Method method;
		final Object[] args;
		
		MethodInvocation( final Method method, final Object[] args ) {
			this.method = method;
			this.args = args;
		}
		
		final boolean is( final String name ) {
			return WriterDelegate.is( this.method, name );
		}
		
		@SuppressWarnings( "unchecked" )
		final < T > T arg( final int index ) {
			return (T)this.args[ index ];
		}
		
		final void write() throws Throwable {
			WriterDelegate.this.invokeOn(
				WriterDelegate.this.coreWriter,
				this.method,
				this.args );
		}
		
		final boolean isConstructor( final Type type ) {
			if ( this.is( "invokespecial" ) ) {
				Type targetType = this.< Type >arg( 0 );
				JavaMethodDescriptor method = this.< JavaMethodDescriptor >arg( 1 );
				
				return type.equals( targetType ) && method.isInit();
			} else {
				return false;
			}
		}
		
		final boolean isDup() {
			return this.is( "dup" );
		}
	}
	
	private final class NewMethodBuffer extends MethodBuffer {
		NewMethodBuffer( final Method method, final Object[] args ) {
			super( method, args );
		}
		
		@Override
		final void writePlaceholder() throws Throwable {
			ReplStateCodeWriter stateCodeWriter = WriterDelegate.this.repl.stateCodeWriter();
			stateCodeWriter.pushUninitialized( this.< Type >arg( 0 ) );
		}
		
		@Override
		final void startWriteNormally() {
			ReplStateCodeWriter stateCodeWriter = WriterDelegate.this.repl.stateCodeWriter();
			stateCodeWriter.suppressStateTracking();
		}
		
		@Override
		final void endWriteNormally() {
			ReplStateCodeWriter stateCodeWriter = WriterDelegate.this.repl.stateCodeWriter();

			stateCodeWriter.restoreStateTracking();

			if ( this.containsDup() ) {
				stateCodeWriter.push( this.< Type >arg( 0 ) );
			}
		}
		
		final boolean containsDup() {
			for ( MethodInvocation invocation : this.followingInvocations ) {
				if ( invocation.isDup() ) {
					return true;
				}
			}
			return false;
		}
		
		@Override
		final boolean isTerminator( final MethodInvocation invocation ) {
			return invocation.isConstructor( this.< Type >arg( 0 ) );
		}
	}
}