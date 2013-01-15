package net.dougqh.jak.jvm.assembler.api;

import static org.junit.Assert.*;

import java.lang.reflect.Type;
import java.util.Arrays;

import net.dougqh.jak.jvm.JvmTypeStack;
import net.dougqh.jak.matchers.Matchers;
import net.dougqh.java.meta.types.JavaTypes;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

//TODO: Relocate
public final class JvmTypeStackTest {
	public final @Test void basics() {
		JvmTypeStack stack = new JvmTypeStack();
		
		assertThat( stack, isEmpty() );
		
		stack.stack( int.class );
		stack.stack( long.class );
		stack.stack( float.class );
		stack.stack( double.class );
		stack.stack( Object.class );
		
		assertThat(
			stack,
			contains( Object.class, double.class, float.class, long.class, int.class ) );
	}
	
	public final @Test void growth() {
		JvmTypeStack stack = new JvmTypeStack( 2 );
		
		stack.stack( int.class );
		stack.stack( long.class );
		stack.stack( float.class );
		stack.stack( double.class );
		stack.stack( Object.class );

		assertThat( stack.size(), is( 5 ) );
	}
	
	public final @Test void unstack() {
		JvmTypeStack stack = new JvmTypeStack();
		
		stack.stack( int.class );
		assertThat( stack.top(), is( int.class ) ); 
		
		stack.unstack( int.class );
		assertThat( stack, isEmpty() );
	}
	
	public final @Test void pop() {
		JvmTypeStack stack = new JvmTypeStack();
		
		stack.stack( float.class );
		stack.stack( int.class );
		assertThat( stack.top(), is( int.class ) );
		
		stack.pop();
		assertThat( stack.top(), is( float.class ) );
		
		stack.pop();
		assertThat( stack, isEmpty() );
	}
	
	public final @Test void pop2withCategory1() {
		JvmTypeStack stack = new JvmTypeStack();
		
		stack.stack( float.class );
		stack.stack( int.class );
		assertThat( stack, contains( int.class, float.class ) );
		
		stack.pop2();
		assertThat( stack, isEmpty() );
	}
	
	public final @Test void pop2withCategory2() {
		JvmTypeStack stack = new JvmTypeStack();
		
		stack.stack( double.class );
		stack.stack( long.class );
		assertThat( stack.top(), is( long.class ) );
		
		stack.pop2();
		assertThat( stack.top(), is( double.class ) );
		
		stack.pop2();
		assertThat( stack, isEmpty() );
	}
	
	public final @Test void swap() {
		JvmTypeStack stack = new JvmTypeStack();
		
		stack.stack( int.class );
		stack.stack( float.class );
		stack.swap();
		
		assertThat( stack, contains( int.class, float.class ) );
	}
	
	public final @Test void dup() {
		JvmTypeStack stack = new JvmTypeStack();
		
		stack.stack( float.class );
		stack.dup();
		
		assertThat( stack, contains( float.class, float.class ) );
	}
	
	public final @Test void dup2withCategory1() {
		JvmTypeStack stack = new JvmTypeStack();
		
		stack.stack( float.class );
		stack.stack( int.class );
		stack.dup2();
		
		assertThat( stack, contains( int.class, float.class, int.class, float.class ) );
	}
	
	public final @Test void dup2WithCategory2() {
		JvmTypeStack stack = new JvmTypeStack();
		
		stack.stack( long.class );
		stack.stack( double.class );
		stack.dup2();
		
		assertThat( stack, contains( double.class, double.class, long.class ) );
	}
	
	public final @Test void dupX1() {
		JvmTypeStack stack = new JvmTypeStack();
		
		stack.stack( int.class );
		stack.stack( float.class );
		stack.dup_x1();
		
		assertThat( stack, contains( float.class, int.class, float.class ) );
	}
	
	public final @Test void dupX2withCategory1() {
		JvmTypeStack stack = new JvmTypeStack();
		
		stack.stack( float.class );
		stack.stack( float.class );
		stack.stack( Object.class );
		stack.dup_x2();
		
		assertThat( stack, contains( Object.class, float.class, float.class, Object.class ) );
	}
	
	public final @Test void dupX2withCategory2() {
		JvmTypeStack stack = new JvmTypeStack();
		
		stack.stack( long.class );
		stack.stack( int.class );
		stack.dup_x2();
		
		assertThat( stack, contains( int.class, long.class, int.class ) );
	}
	
	public final @Test void dup2x1withCategory1() {
		JvmTypeStack stack = new JvmTypeStack();
		
		stack.stack( int.class );
		stack.stack( Object.class );
		stack.stack( float.class );
		stack.dup2_x1();
		
		assertThat( stack, contains( float.class, Object.class, int.class, float.class, Object.class ) );
	}
	
	public final @Test void dup2x1withCategory2() {
		JvmTypeStack stack = new JvmTypeStack();
		
		stack.stack( int.class );
		stack.stack( long.class );
		stack.dup2_x1();
		
		assertThat( stack, contains( long.class, int.class, long.class ) );
	}
	
	public final @Test void dup2x2withCategory2and2() {
		JvmTypeStack stack = new JvmTypeStack();
		
		stack.stack( long.class );
		stack.stack( double.class );
		stack.dup2_x2();
		
		assertThat( stack, contains( double.class, long.class, double.class ) );
	}
	
	public final @Test void dup2x2withCategory2and1() {
		JvmTypeStack stack = new JvmTypeStack();
		
		stack.stack( Object.class );
		stack.stack( int.class );
		stack.stack( long.class );
		stack.dup2_x2();
		
		assertThat( stack, contains( long.class, int.class, Object.class, long.class ) );
	}
	
	public final @Test void dup2x2withCategory1and2() {
		JvmTypeStack stack = new JvmTypeStack();
		
		stack.stack( long.class );
		stack.stack( float.class );
		stack.stack( int.class );
		stack.dup2_x2();
		
		assertThat( stack, contains( int.class, float.class, long.class, int.class, float.class ) );
	}
	
	public final @Test void dup2x2withCategory1and1() {
		JvmTypeStack stack = new JvmTypeStack();
		
		stack.stack( int.class );
		stack.stack( float.class );
		stack.stack( Object.class );
		stack.stack( int.class );
		stack.dup2_x2();
		
		assertThat(
			stack,
			contains( int.class, Object.class, float.class, int.class, int.class, Object.class ) );
	}
	
	private static final < T > Matcher< T > is( final T value ) {
		return Matchers.is( value );
	}
	
	private static final Matcher< Type > is( final Type type ) {
		return Matchers.is( type );
	}
	
	private static final Matcher< JvmTypeStack > contains( final Type... types ) {
		return new ContainsMatcher( types );
	}
	
	private static final Matcher< JvmTypeStack > isEmpty() {
		return new IsEmptyMatcher();
	}
	
	private static final class ContainsMatcher extends BaseMatcher< JvmTypeStack > {
		private final Type[] expectedTypes;
		
		ContainsMatcher( final Type... expectedTypes ) {
			this.expectedTypes = expectedTypes;
		}
		
		@Override
		public final boolean matches( final Object obj ) {
			JvmTypeStack stack = (JvmTypeStack)obj;
			
			return ( stack.size() == this.expectedTypes.length ) &&
				stack.matches( this.expectedTypes );
		}
		
		@Override
		public final void describeTo( final Description description ) {
			String[] expectedTypeNames = new String[ this.expectedTypes.length ];
			for ( int i = 0; i < this.expectedTypes.length; ++i ) {
				expectedTypeNames[ i ] = JavaTypes.getRawClassName( this.expectedTypes[ i ] );
			}
			
			description.appendText( "Expected [" + Arrays.toString( expectedTypeNames ) + "]" );
		}
	}
	
	private static final class IsEmptyMatcher extends BaseMatcher< JvmTypeStack > {
		@Override
		public final boolean matches( final Object obj ) {
			JvmTypeStack stack = (JvmTypeStack)obj;
			return stack.isEmpty();
		}
		
		@Override
		public final void describeTo( final Description description ) {
			description.appendText( "Stack should be empty" );
		}
	}
}
