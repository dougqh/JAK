package net.dougqh.jak.repl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.dougqh.jak.JavaCodeWriter;

final class ReplMethod {
	public static final ReplMethod find( final String name ) {
		for ( Method method : JavaCodeWriter.class.getMethods() ) {
			if ( method.getName().equals( name ) && isWritingMethod( method ) ) {
				return new ReplMethod( method );
			}
		}
		return null;
	}
	
	public static final List< String > findLike( final String prefix ) {
		Method[] methods = JavaCodeWriter.class.getMethods();
		
		ArrayList< String > matchingNames = new ArrayList< String >( methods.length );
		for ( Method method : JavaCodeWriter.class.getMethods() ) {
			if ( method.getName().startsWith( prefix ) && isWritingMethod( method ) ) {
				matchingNames.add( method.getName() );
			}
		}
		return Collections.unmodifiableList( matchingNames );
	}
	
	private final Method method;
	private final ReplArgument[] arguments;
	
	ReplMethod( final Method method ) {
		this.method = method;
		
		Class< ? >[] types = this.method.getParameterTypes();
		
		this.arguments = new ReplArgument[ types.length ];
		for ( int i = 0; i < types.length; ++i ) {
			this.arguments[ i ] = ReplArgument.instance( types[ i ] );
		}
	}
	
	public final String getName() {
		return this.method.getName();
	}
	
	public final ReplArgument[] getArguments() {
		return this.arguments;
	}
	
	public final Object[] parseArguments( final String[] argStrings ) {
		if ( this.arguments.length != argStrings.length ) {
			throw new IllegalArgumentException();
		}
		
		Object[] values = new Object[ argStrings.length ];
		for ( int i = 0; i < argStrings.length; ++i ) {
			values[ i ] = this.arguments[ i ].parse( argStrings[ i ] );
		}
		return values;
	}
	
	public final void invoke(
		final JavaCodeWriter codeWriter,
		final Object... arguments  )
		throws IllegalArgumentException
	{
		try {
			method.invoke( codeWriter, arguments );
		} catch ( IllegalAccessException e ) {
			throw new IllegalStateException( e );
		} catch ( InvocationTargetException e ) {
			throw new IllegalStateException( e );
		}		
	}
	
	private static final boolean isWritingMethod( final Method method ) {
		return method.getDeclaringClass().equals( JavaCodeWriter.class ) &&
			method.getReturnType().equals( JavaCodeWriter.class );
	}
}
