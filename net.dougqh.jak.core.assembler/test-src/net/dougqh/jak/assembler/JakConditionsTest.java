package net.dougqh.jak.assembler;

import static net.dougqh.jak.assembler.JakAsm.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Type;

import net.dougqh.jak.JakContext;

import org.junit.Test;

public final class JakConditionsTest {
	private static final JakExpression lhsExpr = new FakeJakExpression();
	private static final JakExpression rhsExpr = new FakeJakExpression();
	private static final JakExpression expr = new FakeJakExpression();
	
	private static final JakCondition lhsCond = eq( lhsExpr, rhsExpr );
	private static final JakCondition rhsCond = eq( lhsExpr, rhsExpr );
	
	@Test
	public final void equalTo() {
		eq( lhsExpr, rhsExpr ).accept( new TestVisitor() {
			@Override
			protected final void eq( final JakExpression lhs, final JakExpression rhs ) {
				assertThat( lhs, is( lhsExpr ) );
				assertThat( rhs, is( rhsExpr ) );
			}
		} );
		assertThat( eq( lhsExpr, rhsExpr ).inverse(), is( ne( lhsExpr, rhsExpr ) ) );
	}

	@Test
	public final void notEqual() {
		ne( lhsExpr, rhsExpr ).accept( new TestVisitor() {
			@Override
			protected final void ne( final JakExpression lhs, final JakExpression rhs ) {
				assertThat( lhs, is( lhsExpr ) );
				assertThat( rhs, is( rhsExpr ) );
			}
		} );
		assertThat( ne( lhsExpr, rhsExpr ).inverse(), is( eq( lhsExpr, rhsExpr ) ) );
	}
	
	@Test
	public final void lessThan() {
		lt( lhsExpr, rhsExpr ).accept( new TestVisitor() {
			@Override
			protected final void lt( final JakExpression lhs, final JakExpression rhs ) {
				assertThat( lhs, is( lhsExpr ) );
				assertThat( rhs, is( rhsExpr ) );
			}
		} );
		assertThat( lt( lhsExpr, rhsExpr ).inverse(), is( ge( lhsExpr, rhsExpr ) ) );
	}
	
	@Test
	public final void lessThanOrEqualTo() {
		le( lhsExpr, rhsExpr ).accept( new TestVisitor() {
			@Override
			protected final void le( final JakExpression lhs, final JakExpression rhs ) {
				assertThat( lhs, is( lhsExpr ) );
				assertThat( rhs, is( rhsExpr ) );
			}
		} );
		assertThat( le( lhsExpr, rhsExpr ).inverse(), is( gt( lhsExpr, rhsExpr ) ) );
	}

	@Test
	public final void greaterThan() {
		gt( lhsExpr, rhsExpr ).accept( new TestVisitor() {
			@Override
			protected final void gt( final JakExpression lhs, final JakExpression rhs ) {
				assertThat( lhs, is( lhsExpr ) );
				assertThat( rhs, is( rhsExpr ) );
			}
		} );
		assertThat( gt( lhsExpr, rhsExpr ).inverse(), is( le( lhsExpr, rhsExpr ) ) );
	}
	
	@Test
	public final void greaterThanOrEqual() {
		ge( lhsExpr, rhsExpr ).accept( new TestVisitor() {
			@Override
			protected final void ge( final JakExpression lhs, final JakExpression rhs ) {
				assertThat( lhs, is( lhsExpr ) );
				assertThat( rhs, is( rhsExpr ) );
			}
		} );
		assertThat( ge( lhsExpr, rhsExpr ).inverse(), is( lt( lhsExpr, rhsExpr ) ) );
	}
	
	@Test
	public final void truthiness() {
		truthy( expr ).accept( new TestVisitor() {
			@Override
			protected final void truthy( final JakExpression expr ) {
				assertThat( expr, is( JakConditionsTest.expr ) );
			}
		} );
		assertThat( truthy( expr ).inverse(), is( falsy( expr ) ) );
	}
	
	@Test
	public final void falsiness() {
		falsy( expr ).accept( new TestVisitor() {
			@Override
			protected final void falsy( final JakExpression expr ) {
				assertThat( expr, is( JakConditionsTest.expr ) );
			}
		} );
		assertThat( falsy( expr ).inverse(), is( truthy( expr ) ) );
	}
	
	@Test
	public final void andingDirect() {
		and( lhsCond, rhsCond ).accept( new TestVisitor() {
			@Override
			protected void and( final JakCondition lhs, final JakCondition rhs ) {
				assertThat( lhs, is( lhsCond ) );
			}
		} );
	}
	
	@Test
	public final void andingChain() {
		lhsCond.and( rhsCond ).accept( new TestVisitor() {
			@Override
			protected void and( final JakCondition lhs, final JakCondition rhs ) {
				assertThat( lhs, is( lhsCond ) );
			}
		} );		
	}
	
	@Test
	public final void oringDirect() {
		or( lhsCond, rhsCond ).accept( new TestVisitor() {
			@Override
			protected void or( final JakCondition lhs, final JakCondition rhs ) {
				assertThat( lhs, is( lhsCond ) );
			}
		} );	
	}
	
	@Test
	public final void oringChain() {
		lhsCond.or( rhsCond ).accept( new TestVisitor() {
			@Override
			protected void or( final JakCondition lhs, final JakCondition rhs ) {
				assertThat( lhs, is( lhsCond ) );
			}
		} );		
	}
	
	static class TestVisitor extends JakCondition.Visitor {
		@Override
		protected void eq(
			final JakExpression lhs,
			final JakExpression rhs )
		{
			throw new UnsupportedOperationException();
		}
		
		@Override
		protected void ne(
			final JakExpression lhs,
			final JakExpression rhs )
		{
			throw new UnsupportedOperationException();
		}
		
		@Override
		protected void ge(
			final JakExpression lhs,
			final JakExpression rhs )
		{
			throw new UnsupportedOperationException();
		}
		
		@Override
		protected void gt(
			final JakExpression lhs,
			final JakExpression rhs )
		{
			throw new UnsupportedOperationException();
		}
		
		@Override
		protected void le(
			final JakExpression lhs,
			final JakExpression rhs )
		{
			throw new UnsupportedOperationException();
		}
		
		@Override
		protected void lt(
			final JakExpression lhs,
			final JakExpression rhs )
		{
			throw new UnsupportedOperationException();
		}
		
		@Override
		protected void truthy( final JakExpression expr ) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		protected void falsy( final JakExpression expr ) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		protected void and(
			final JakCondition lhs,
			final JakCondition rhs )
		{
			throw new UnsupportedOperationException();
		}
		
		@Override
		protected void or(
			final JakCondition lhs,
			final JakCondition rhs )
		{
			throw new UnsupportedOperationException();
		}
	}
	
	private static final class FakeJakExpression extends JakExpression {
		@Override
		public final void accept( final Visitor visitor ) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public final Type type( final JakContext context ) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public final boolean hasConstantValue( final JakContext context ) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public final Object constantValue( final JakContext context ) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public final int hashCode() {
			return 0;
		}
		
		@Override
		public final boolean equals( final Object obj ) {
			return ( obj == this );
		}
		
		@Override
		public final String toString() {
			return "fake";
		}
	}
}
