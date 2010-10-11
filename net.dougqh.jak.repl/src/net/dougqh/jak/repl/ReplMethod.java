package net.dougqh.jak.repl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.dougqh.jak.JavaCodeWriter;
import net.dougqh.jak.annotations.Op;
import net.dougqh.jak.annotations.SyntheticOp;
import net.dougqh.jak.annotations.WrapOp;
import net.dougqh.jak.operations.Operation;
import net.dougqh.jak.operations.Operations;

final class ReplMethod {
	public static final Set< ReplMethod > find( final String name ) {
		HashSet< ReplMethod > matchingMethods = new HashSet< ReplMethod >( 4 );
		
		for ( Method method : JavaCodeWriter.class.getMethods() ) {
			if ( isWritingMethod( method ) ) {
				if ( getNameOf( method ).equals( name ) ) {
					matchingMethods.add( new ReplMethod( method ) );
				}
			}
		}
		return Collections.unmodifiableSet( matchingMethods );
	}
	
	public static final List< String > findLike( final String prefix ) {
		Method[] methods = JavaCodeWriter.class.getMethods();
		
		ArrayList< String > matchingNames = new ArrayList< String >( methods.length );
		for ( Method method : JavaCodeWriter.class.getMethods() ) {
			if ( isWritingMethod( method ) ) {
				String methodName = getNameOf( method );
				if ( methodName.startsWith( prefix ) ) {
					matchingNames.add( methodName );
				}
			}
		}
		return Collections.unmodifiableList( matchingNames );
	}
	
	private final Method method;
	private final String name;
	private final ReplArgument[] arguments;
	
	ReplMethod( final Method method ) {
		this.method = method;
		this.name = getNameOf( method );
		
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
	
	private static final String getNameOf( final Method method ) {
		SyntheticOp synthOp = method.getAnnotation( SyntheticOp.class );
		if ( synthOp != null ) {
			return synthOp.id().isEmpty() ? method.getName() : synthOp.id();
		}
		return getOperationOf( method ).getId();
	}
	
	private static final Operation getOperationOf( final Method method ) {
		Op op = method.getAnnotation( Op.class );
		if ( op != null ) {
			return Operations.getPrototype( op.value() );
		}
		WrapOp wrapOp = method.getAnnotation( WrapOp.class );
		if ( wrapOp != null ) {
			return Operations.getPrototype( wrapOp.value() );
		}
		throw new IllegalStateException();
	}
}
