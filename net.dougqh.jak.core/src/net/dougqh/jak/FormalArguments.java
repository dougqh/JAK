package net.dougqh.jak;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.ListIterator;

import net.dougqh.java.meta.types.JavaTypes;

public final class FormalArguments implements Iterable< JavaVariable > {
	public static final FormalArguments EMPTY = new FormalArguments();
	
	private final JavaVariable[] arguments;
	
	public static final FormalArguments instance( final Type... argumentTypes ) {
		if ( argumentTypes.length == 0 ) {
			return EMPTY;
		} else {
			JavaVariable[] variables = new JavaVariable[ argumentTypes.length ];
			for ( int i = 0; i < variables.length; ++i ) {
				variables[ i ] = new JavaVariable( argumentTypes[ i ], "var" + i );
			}
			return new FormalArguments( variables );
		}
	}
	
	public static final FormalArguments instance( final JavaVariable... arguments ) {
		if ( arguments.length == 0 ) {
			return EMPTY;
		} else {
			return new FormalArguments( arguments );
		}
	}
	
	public static final FormalArguments instance(
		final FormalArguments existingArguments,
		final JavaVariable var )
	{
		JavaVariable[] newArguments = Arrays.copyOf(
			existingArguments.arguments,
			existingArguments.arguments.length + 1 );
		newArguments[ existingArguments.arguments.length ] = var;
		
		return FormalArguments.instance( newArguments );
	}
	
	private FormalArguments( final JavaVariable... arguments ) {
		this.arguments = arguments;
	}
	
	@Deprecated
	public final Class< ? >[] getRawClasses() {
		Class< ? >[] rawClasses = new Class< ? >[ this.arguments.length ];
		for ( int i = 0; i < this.arguments.length; ++i ) {
			rawClasses[ i ] = JavaTypes.getRawClass( this.arguments[ i ].getType() );
		}
		return rawClasses;
	}
	
	public final Type[] getTypes() {
		Type[] types = new Type[ this.arguments.length ];
		for ( int i = 0; i < this.arguments.length; ++i ) {
			types[ i ] = this.arguments[ i ].getType();
		}
		return types;
	}
	
	public final int count() {
		return this.arguments.length;
	}
	
	@Override
	public final ListIterator< JavaVariable > iterator() {
		return Collections.unmodifiableList( Arrays.asList( this.arguments ) ).listIterator();
	}
	
	@Override
	public final int hashCode() {
		return Arrays.hashCode( this.arguments );
	}
	
	@Override
	public final boolean equals(Object obj) {
		if ( obj == this ) {
			return true;
		} else if ( ! ( obj instanceof FormalArguments ) ) {
			return false;
		} else {
			FormalArguments that = (FormalArguments)obj;
			return Arrays.deepEquals( this.arguments, that.arguments );
		}
	}
	
	@Override
	public final String toString() {
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for ( JavaVariable arg: this.arguments ) {
			if ( first ) {
				first = false;
			} else {
				builder.append( ", " );
			}
			builder.append( arg );
		}
		return builder.toString();
	}
}
