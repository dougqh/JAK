package net.dougqh.jak.matchers;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;

/**
 * Wrapper around {@link CoreMatchers} that is created to contain 
 * matchers used by JAK test classes.
 * 
 * This was originally done because JAK frequently needs to equivalence 
 * checks with Class< T > objects which is unwieldy with the 
 * default {@link CoreMatchers#is(Class)} implementation.
 */
public final class Matchers {
	private Matchers() {}
	
	public static final < T > Matcher< T > is( final T value ) {
		return CoreMatchers.< T >is( value );
	}
	
	public static final Matcher< Class< ? > > is( final Class< ? > aClass ) {
		return CoreMatchers.< Class< ? > >is( aClass );
	}
	
	public static final Matcher< Object > isA( final Class< ? > aClass ) {
		return CoreMatchers.is( aClass );
	}
}
