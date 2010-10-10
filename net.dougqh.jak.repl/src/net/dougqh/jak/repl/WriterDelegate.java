package net.dougqh.jak.repl;

import java.lang.reflect.Method;

import net.dougqh.jak.JavaCoreCodeWriter;
import net.dougqh.reflection.Delegate;

final class WriterDelegate extends Delegate< JavaCoreCodeWriter > {	
	private final JakRepl repl;
	private final JavaCoreCodeWriter coreWriter;

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
			this.repl.console().print( interfaceMethod, args );
			this.repl.recorder().record( interfaceMethod, args );
		}
	}
	
	@Override
	protected final Object invoke(
		final Method interfaceMethod,
		final Object[] args )
		throws Throwable
	{
		return this.invokeOn( this.coreWriter, interfaceMethod, args );
	}
	
	private static final boolean isCoreWritingMethod( final Method method ) {
		return method.getDeclaringClass().equals( JavaCoreCodeWriter.class ) &&
			method.getReturnType().equals( JavaCoreCodeWriter.class );
	}

}