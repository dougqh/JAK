package net.dougqh.jak.repl;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.reflection.Delegate;

final class WriterDelegate extends Delegate< JavaCoreCodeWriter > {	
	private final JakRepl repl;
	private final JavaCoreCodeWriter coreWriter;
	
	private MethodBuffer buffer = null;

	WriterDelegate(
		final JakRepl repl,
		final JavaCoreCodeWriter coreWriter )
	{
		super( JavaCoreCodeWriter.class );
		
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
				return this.startBuffering( new NewMethodInvocation( interfaceMethod, args ) );
			} else {
				return this.invoke( new MethodInvocation( interfaceMethod, args ) );
			}
		} else {
			if ( is( interfaceMethod, "prepareForWrite" ) ) {
				this.prepareForWrite();
				return null;
			} else {
				return this.invokeOn( this.coreWriter, interfaceMethod, args );
			}
		}
	}
	
	private static final boolean is( final Method method, final String name ) {
		return method.getName().equals( name );
	}
	
	private final void prepareForWrite() {
		if ( this.buffer != null ) {
			MethodBuffer oldBuffer = this.buffer;
			this.buffer = null;
			
			try {
				oldBuffer.writePlaceholders();
			} catch ( Throwable e ) {
				throw new IllegalStateException( e );
			}
		}
		this.coreWriter.prepareForWrite();
	}
	
	private final JavaCoreCodeWriter startBuffering( final PendingMethodInvocation invocation ) {
		this.buffer = new MethodBuffer( invocation );
		return this.getProxy();
	}
	
	private final JavaCoreCodeWriter invoke( final MethodInvocation invocation )
		throws Throwable
	{
		if ( this.buffer != null ) {
			if ( this.buffer.isTerminator( invocation ) ) {
				MethodBuffer oldBuffer = this.buffer;
				this.buffer = null;
				oldBuffer.write();
				this.invokeOn( this.coreWriter, invocation.method, invocation.args );
			} else {
				this.buffer.add( invocation );
			}
			return this.getProxy();
		} else {
			this.invokeOn( this.coreWriter, invocation.method, invocation.args );
			return this.getProxy();
		}
	}
	
	private static final boolean isCoreWritingMethod( final Method method ) {
		return method.getDeclaringClass().equals( JavaCoreCodeWriter.class ) &&
			method.getReturnType().equals( JavaCoreCodeWriter.class );
	}
	
	private static final class MethodBuffer {
		private final PendingMethodInvocation pendingInvocation;
		private final ArrayList< MethodInvocation > followingInvocations = 
			new ArrayList< MethodInvocation >( 4 );
		
		MethodBuffer( final PendingMethodInvocation pendingInvocation ) {
			this.pendingInvocation = pendingInvocation;
		}
		
		final void add( final MethodInvocation invocation ) {
			this.followingInvocations.add( invocation );
		}
		
		final void write() throws Throwable {
			this.pendingInvocation.write();
			for ( MethodInvocation invocation : this.followingInvocations ) {
				invocation.write();
			}
		}
		
		final void writePlaceholders() throws Throwable {
			this.pendingInvocation.writePlaceholder();
			for ( MethodInvocation invocation : this.followingInvocations ) {
				invocation.writePlaceholder();
			}
		}
		
		final boolean isTerminator( final MethodInvocation invocation ) {
			return this.pendingInvocation.isTerminator( invocation );
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
		
		void writePlaceholder() throws Throwable {
			this.write();
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
	}
	
	private abstract class PendingMethodInvocation extends MethodInvocation {
		PendingMethodInvocation( final Method method, final Object[] args ) {
			super( method, args );
		}
		
		@Override
		abstract void writePlaceholder() throws Throwable;
		
		abstract boolean isTerminator( final MethodInvocation invocation );
	}
	
	private final class NewMethodInvocation extends PendingMethodInvocation {
		NewMethodInvocation( final Method method, final Object[] args ) {
			super( method, args );
		}
		
		@Override
		final void writePlaceholder() throws Throwable {
			WriterDelegate.this.coreWriter.aconst_null();
		}
		
		@Override
		final boolean isTerminator( final MethodInvocation invocation ) {
			return invocation.isConstructor( this.< Type >arg( 0 ) );
		}
	}
}