package net.dougqh.jak.repl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.dougqh.jak.JavaMethodSignature;
import net.dougqh.jak.assembler.JakCondition;
import net.dougqh.jak.assembler.JakExpression;
import net.dougqh.jak.assembler.JakMacro;
import net.dougqh.jak.jvm.annotations.JvmOp;
import net.dougqh.jak.jvm.annotations.Symbol;
import net.dougqh.jak.jvm.annotations.SyntheticOp;
import net.dougqh.jak.jvm.annotations.WrapOp;
import net.dougqh.jak.jvm.assembler.JvmCodeWriter;
import net.dougqh.jak.jvm.operations.JvmOperation;
import net.dougqh.jak.jvm.operations.JvmOperations;
import net.dougqh.java.meta.types.JavaTypes;

final class ReplMethod {
	private static final List< ReplMethod > allMethods = gatherMethods();
	
	private static final List< ReplMethod > gatherMethods() {
		Method[] methods = JvmCodeWriter.class.getMethods();
		
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
		Collections.sort( replMethods, new MethodComparator() );
		
		return Collections.unmodifiableList( replMethods );
	}
	
	public static final Set< ReplMethod > findByOperator( final String operator ) {
		//Preservation of order is important
		LinkedHashSet< ReplMethod > matchingMethods = new LinkedHashSet< ReplMethod >( 4 );
		
		for ( ReplMethod replMethod : allMethods ) {
			if ( operator.equals( replMethod.getOperator() ) ) {
				matchingMethods.add( replMethod );
			}
		}
		return Collections.unmodifiableSet( matchingMethods );
	}
	
	public static final Set< ReplMethod > findByName( final String name ) {
		//Preservation of order is important
		LinkedHashSet< ReplMethod > matchingMethods = new LinkedHashSet< ReplMethod >( 4 );
		
		for ( ReplMethod method : allMethods ) {
			if ( method.getName().equals( name ) ) {
				matchingMethods.add( method );
			}
		}
		return Collections.unmodifiableSet( matchingMethods );
	}
	
	public static final Set< String > findLike( final String prefix ) {
		//Preservation of order is important
		LinkedHashSet< String > methodNames = new LinkedHashSet< String >();
		
		for ( ReplMethod method : allMethods ) {
			if ( method.getName().startsWith( prefix ) ) {
				methodNames.add( method.getName() );
			}
		}
		return Collections.unmodifiableSet( methodNames );
	}
	
	private final Method method;
	private final String name;
	private final ReplArgument[] arguments;
	
	private ReplMethod( final Method method ) {
		this.method = method;
		this.name = getNameOf( method );
		
		Class< ? >[] types = this.method.getParameterTypes();
		Annotation[][] parameterAnnotations = this.method.getParameterAnnotations();
		
		this.arguments = new ReplArgument[ types.length ];
		for ( int i = 0; i < types.length; ++i ) {
			this.arguments[ i ] = ReplArgument.find(
				types[ i ],
				isAnnotationPresent( parameterAnnotations[ i ], Symbol.class ) );
		}
	}
	
	private static final boolean isAnnotationPresent(
		final Annotation[] annotations,
		final Class< ? extends Annotation > annoClass )
	{
		for ( Annotation annotation: annotations ) {
			if ( annotation.annotationType().equals( annoClass ) ) {
				return true;
			}
		}
		return false;
	}
	
	public final String getName() {
		return this.name;
	}
	
	public final boolean isSynthetic() {
		return this.method.isAnnotationPresent( SyntheticOp.class );
	}
	
	public final boolean matchesStackTypes( final JakRepl repl ) {
		JvmOperation operation = getOperationOf( this.method );
		
		Type[] expectedTypes = operation.getStackOperandTypes().clone();
		reverse( expectedTypes );
		
		return repl.codeWriter().stackMonitor().typeStack().matches( expectedTypes );
	}
	
	private final void reverse( final Object[] array ) {
		for ( int i = 0; i < array.length / 2; ++i ) {
			Object tmp = array[ i ];
			array[ i ] = array[ array.length - 1 ];
			array[ array.length - 1 ] = tmp;
		}
	}
	
	public final String getOperator() {
		JvmOperation operation = getOperationOf( this.method );
		if ( operation != null ) {
			return operation.getOperator();
		} else {
			return null;
		}
	}
	
	public final ReplArgument[] getArguments() {
		return this.arguments;
	}
	
	public final Object[] parseArguments( final JakRepl repl, final List< String > argStrings ) {
		if ( this.arguments.length != argStrings.size() ) {
			throw new IllegalArgumentException();
		}
		
		Object[] values = new Object[ argStrings.size() ];
		for ( int i = 0; i < argStrings.size(); ++i ) {
			values[ i ] = this.arguments[ i ].parse( repl, argStrings.get( i ) );
		}
		return values;
	}
	
	public final void invoke(
		final JvmCodeWriter codeWriter,
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
		
		JvmOp op = method.getAnnotation( JvmOp.class );
		if ( op != null && ! op.repl() ) {
			return false;
		}
		WrapOp wrapOp = method.getAnnotation( WrapOp.class );
		if ( wrapOp != null && ! wrapOp.repl() ) {
			return false;
		}
		SyntheticOp syntheticOp = method.getAnnotation( SyntheticOp.class );
		if ( syntheticOp != null && ! syntheticOp.repl() ) {
			return false;
		}
		
		Class< ? >[] argTypes = method.getParameterTypes();
		if ( containsCollectionType( argTypes ) ) {
			return false;
		}
		if ( containsMacroType( argTypes ) ) {
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
	
	private static final boolean containsCollectionType( final Type[] types ) {
		for ( Type curType : types ) {
			if ( JavaTypes.isArrayType( curType ) ||
				JavaTypes.isObjectType( Collection.class ) )
			{
				return true;
			}
		}
		return false;
	}
	
	private static final boolean containsMacroType( final Type[] types ) {
		for ( Type curType : types ) {
			if ( curType.equals( JakMacro.class ) ) {
				return true;
			} else if ( curType.equals( JakExpression.class ) ) {
				return true;
			} else if ( curType.equals( JakCondition.class ) ) {
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
		return method.getDeclaringClass().equals( JvmCodeWriter.class ) &&
			method.getReturnType().equals( JvmCodeWriter.class );
	}
	
	private static final String getNameOf( final Method method ) {
		JvmOp op = method.getAnnotation( JvmOp.class );
		if ( op != null ) {
			return JvmOperations.getPrototype( op.value() ).getId();
		}
		
		WrapOp wrapOp = method.getAnnotation( WrapOp.class );
		if ( wrapOp != null ) {
			return method.getName();
		}
		
		SyntheticOp synthOp = method.getAnnotation( SyntheticOp.class );
		if ( synthOp != null ) {
			return synthOp.id().isEmpty() ? method.getName() : synthOp.id();
		}
		
		throw new IllegalStateException( "Unannotated method " + method );
	}
	
	private static final JvmOperation getOperationOf( final Method method ) {
		JvmOp op = method.getAnnotation( JvmOp.class );
		if ( op != null ) {
			return JvmOperations.getPrototype( op.value() );
		}
		WrapOp wrapOp = method.getAnnotation( WrapOp.class );
		if ( wrapOp != null ) {
			return JvmOperations.getPrototype( wrapOp.value() );
		}
		return null;
	}
	
	private static final class MethodComparator implements Comparator< ReplMethod > {
		@Override
		public final int compare(
			final ReplMethod lhs,
			final ReplMethod rhs )
		{
			//Compare by name
			int nameCompare = lhs.getName().compareToIgnoreCase( rhs.getName() );
			if ( nameCompare != 0 ) {
				return nameCompare;
			}
			
			//Then argument length
			if ( lhs.arguments.length < rhs.arguments.length ) {
				return -1;
			} else if ( lhs.arguments.length > rhs.arguments.length ) {
				return 1;
			}
			
			//Finally, argument type - sort Symbol last, so that ints,
			//method descriptors, etc. are used as possible matches first
			for ( int i = 0; i < lhs.arguments.length; ++i ) {
				ReplArgument lhsArg = lhs.arguments[ i ];
				ReplArgument rhsArg = rhs.arguments[ i ];
				
				//Symbols sort last
				if ( rhsArg == ReplArgument.SYMBOL ) {
					return -1;
				} else if ( lhsArg == ReplArgument.SYMBOL ) {
					return 1;
				}
			}
			
			//Otherwise, equivalent
			return 0;
		}
	}
}
