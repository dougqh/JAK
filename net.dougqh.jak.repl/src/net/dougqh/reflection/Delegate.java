package net.dougqh.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public abstract class Delegate< I > {
	private final Class< ? > anInterface;
	private final I proxy;

	@SuppressWarnings( "unchecked" )
	protected Delegate( final Class< ? > anInterface ) {
		this.anInterface = anInterface;
		
		this.proxy = (I)Proxy.newProxyInstance(
			this.getClass().getClassLoader(),
			new Class[] { anInterface },
			new InvocationHandlerImpl() );
	}
	
	@SuppressWarnings( "unchecked" )
	protected Delegate(
		final ClassLoader classLoader,
		final Class< ? > anInterface )
	{
		this.anInterface = anInterface;
		
		this.proxy = (I)Proxy.newProxyInstance(
			classLoader,
			new Class[] { anInterface },
			new InvocationHandlerImpl() );
	}

	public final I getProxy() {
		return this.proxy;
	}
	
	protected final Class< ? > getInterface() {
		return this.anInterface;
	}	
	
	protected void before(
		final Method interfaceMethod,
		final Object[] args )
	{
	}
	
	protected abstract Object invoke(
		final Method interfaceMethod,
		final Object[] args )
		throws Throwable;
	
	protected void after(
		final Method interfaceMethod,
		final Object[] args,
		final Object result )
	{
	}
	
	protected void afterException(
		final Method interfaceMethod,
		final Object[] args,
		final Throwable cause )
		throws Throwable
	{
	}
	
	protected final Object invokeMyMethod(
		final Method interfaceMethod,
		final Object[] args )
		throws Throwable
	{
		Method myMethod = this.getClass().getMethod(
			interfaceMethod.getName(),
			interfaceMethod.getParameterTypes() );
		
		return myMethod.invoke( this, args );
	}
	
	protected final Object invokeOn(
		final I wrappedObject,
		final Method interfaceMethod,
		final Object[] args )
		throws Throwable
	{
		interfaceMethod.setAccessible(true);
		return interfaceMethod.invoke(wrappedObject, args);
	}
	
	private final class InvocationHandlerImpl implements InvocationHandler {
		@Override
		public final Object invoke(
			final Object proxy,
			final Method method,
			final Object[] args )
			throws Throwable
		{
			Object[] sanitizedArgs;
			if ( args == null ) {
				sanitizedArgs = new Object[] {};
			} else {
				sanitizedArgs = args;
			}
			
			Delegate.this.before( method, sanitizedArgs );
			try {
				Object result = Delegate.this.invoke( method, sanitizedArgs );				
				Delegate.this.after( method, sanitizedArgs, result );
				return result;
			} catch ( Throwable e ) {
				Delegate.this.afterException( method, sanitizedArgs, e );
				throw e;
			}
		}
	}
}
