package net.dougqh.jak.repl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import net.dougqh.jak.JavaCodeWriter;
import net.dougqh.jak.JavaMethodSignature;
import net.dougqh.jak.annotations.Op;
import net.dougqh.jak.annotations.SyntheticOp;
import net.dougqh.jak.annotations.WrapOp;
import net.dougqh.jak.operations.Operation;
import net.dougqh.jak.operations.Operations;
import net.dougqh.java.meta.types.JavaTypes;

final class ReplMethod {
	private static final List< ReplMethod > allMethods = gatherMethods();
	
	private static final List< ReplMethod > gatherMethods() {
		Method[] methods = JavaCodeWriter.class.getMethods();
		
		ArrayList< ReplMethod > replMethods = new ArrayList< ReplMethod >( methods.length );
		for ( Method method : methods ) {
			if ( include( method ) ) {
				try {
					replMethods.add( new ReplMethod( method ) );
				} catch ( Throwable t ) {
					System.err.println( method.getName() );
					t.printStackTrace();
					return Collections.emptyList();
				}
			}
		}
		replMethods.trimToSize();
		
		return Collections.unmodifiableList( replMethods );
	}
	
	public static final Set< ReplMethod > findByOperator( final String operator ) {
		HashSet< ReplMethod > matchingMethods = new HashSet< ReplMethod >( 4 );
		
		for ( ReplMethod replMethod : allMethods ) {
			if ( operator.equals( replMethod.getOperator() ) ) {
				matchingMethods.add( replMethod );
			}
		}
		return Collections.unmodifiableSet( matchingMethods );
	}
	
	public static final Set< ReplMethod > findByName( final String name ) {
		HashSet< ReplMethod > matchingMethods = new HashSet< ReplMethod >( 4 );
		
		for ( ReplMethod method : allMethods ) {
			if ( method.getName().equals( name ) ) {
				matchingMethods.add( method );
			}
		}
		return Collections.unmodifiableSet( matchingMethods );
	}
	
	public static final List< String > findLike( final String prefix ) {
		Method[] methods = JavaCodeWriter.class.getMethods();
		
		ArrayList< String > matchingNames = new ArrayList< String >( methods.length );
		for ( Method method : JavaCodeWriter.class.getMethods() ) {
			if ( include( method ) ) {
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
		return this.name;
	}
	
	public final boolean matchesStackTypes( final JakRepl repl ) {
		Operation operation = getOperationOf( this.method );
		
		List< Class< ? > > expectedTypes = Arrays.asList( operation.getStackOperandTypes() );
		
		int offset = 0;
		for ( ListIterator< Class< ? > > expectedIter = expectedTypes.listIterator( expectedTypes.size() );
			expectedIter.hasPrevious();
			++offset )
		{
			Class< ? > expectedType = expectedIter.previous();
			Type actualType = repl.topType( offset );
			
			if ( ! expectedType.equals( actualType ) ) {
				return false;
			}
		}
		return true;
	}
	
	public final String getOperator() {
		Operation operation = getOperationOf( this.method );
		if ( operation != null ) {
			return operation.getOperator();
		} else {
			return null;
		}
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
	
	private static final boolean include( final Method method ) {
		if ( ! isWritingMethod( method ) ) {
			return false;
		}
		Class< ? >[] argTypes = method.getParameterTypes();
		if ( containsArrayType( argTypes ) ) {
			return false;
		}
		if ( contains( argTypes, Method.class ) ) {
			return false;
		}
		if ( contains( argTypes, Field.class ) ) {
			return false;
		}
		if ( contains( argTypes, JavaMethodSignature.class ) ) {
			return false;
		}
		return true;
	}
	
	private static final boolean containsArrayType( final Type[] types ) {
		for ( Type curType : types ) {
			if ( JavaTypes.isArrayType( curType ) ) {
				return true;
			}
		}
		return false;
	}
	
	private static final boolean contains( final Type[] types, final Type type ) {
		for ( Type curType : types ) {
			if ( curType.equals( type ) ) {
				return true;
			}
		}
		return false;
	}
	
	private static final boolean isWritingMethod( final Method method ) {
		return method.getDeclaringClass().equals( JavaCodeWriter.class ) &&
			method.getReturnType().equals( JavaCodeWriter.class );
	}
	
	private static final String getNameOf( final Method method ) {
		Operation operation = getOperationOf( method );
		if ( operation != null ) {
			return operation.getId();
		} else {
			SyntheticOp synthOp = method.getAnnotation( SyntheticOp.class );
			if ( synthOp != null ) {
				return synthOp.id().isEmpty() ? method.getName() : synthOp.id();
			}
			throw new IllegalStateException();
		}
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
		return null;
	}
}
