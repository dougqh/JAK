package net.dougqh.jak.jvm.operations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class JvmOperations {
	public static final < T extends JvmOperation > T getPrototype(
		final Class< T > operationClass )
	{
		try {
			Method instanceMethod = operationClass.getMethod( "instance" );
			return invoke( instanceMethod );
		} catch ( NoSuchMethodException e ) {
			try {
				Method prototypeMethod = operationClass.getMethod( "prototype" );
				return invoke( prototypeMethod );
			} catch ( NoSuchMethodException e2 ) {
				throw new IllegalStateException( e );
			}
		}
	}
	
	public static final boolean isA(
		final Class<? extends JvmOperation> operationClass,
		final Class<? extends JvmOperation> desiredClass )
	{
		return desiredClass.isAssignableFrom( operationClass );
	}
	
	@SuppressWarnings( "unchecked" )
	private static final < T extends JvmOperation > T invoke( final Method method ) {
		try {
			return (T)method.invoke( null );
		}  catch ( SecurityException e ) {
			throw new IllegalStateException( e );
		} catch ( IllegalArgumentException e ) {
			throw new IllegalStateException( e );
		} catch ( IllegalAccessException e ) {
			throw new IllegalStateException( e );
		} catch ( InvocationTargetException e ) {
			throw new IllegalStateException( e );
		}		 
	}
}
