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
		assertThat( not( eq( lhsExpr, rhsExpr ) ), is( ne( lhsExpr, rhsExpr ) ) );
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
		assertThat( not( ne( lhsExpr, rhsExpr ) ), is( eq( lhsExpr, rhsExpr ) ) );
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
		assertThat( not( lt( lhsExpr, rhsExpr ) ), is( ge( lhsExpr, rhsExpr ) ) );
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
		assertThat( not( le( lhsExpr, rhsExpr ) ), is( gt( lhsExpr, rhsExpr ) ) );
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
		assertThat( not( gt( lhsExpr, rhsExpr ) ), is( le( lhsExpr, rhsExpr ) ) );
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
		assertThat( not( ge( lhsExpr, rhsExpr ) ), is( lt( lhsExpr, rhsExpr ) ) );
	}
	
	@Test
	public final void truthiness() {
		truthy( expr ).accept( new TestVisitor() {
			@Override
			protected final void truthy( final JakExpression expr ) {
				assertThat( expr, is( JakConditionsTest.expr ) );
			}
		} );
		assertThat( not( truthy( expr ) ), is( falsy( expr ) ) );
	}
	
	@Test
	public final void falsiness() {
		falsy( expr ).accept( new TestVisitor() {
			@Override
			protected final void falsy( final JakExpression expr ) {
				assertThat( expr, is( JakConditionsTest.expr ) );
			}
		} );
		assertThat( not( falsy( expr ) ), is( truthy( expr ) ) );
	}
	
	@Test
	public final void andingDirect() {
		and( lhsCond, rhsCond ).accept( new TestVisitor() {
			@Override
			protected void and( final JakCondition lhs, final JakCondition rhs ) {
				assertThat( lhs, is( lhsCond ) );
				assertThat( rhs, is( rhsCond ) );
			}
		} );
	}
	
	@Test
	public final void andingChain() {
		lhsCond.and( rhsCond ).accept( new TestVisitor() {
			@Override
			protected void and( final JakCondition lhs, final JakCondition rhs ) {
				assertThat( lhs, is( lhsCond ) );
				assertThat( rhs, is( rhsCond ) );
			}
		} );		
	}
	
	@Test
	public final void oringDirect() {
		or( lhsCond, rhsCond ).accept( new TestVisitor() {
			@Override
			protected void or( final JakCondition lhs, final JakCondition rhs ) {
				assertThat( lhs, is( lhsCond ) );
				assertThat( rhs, is( rhsCond ) );
			}
		} );	
	}
	
	@Test
	public final void oringChain() {
		lhsCond.or( rhsCond ).accept( new TestVisitor() {
			@Override
			protected void or( final JakCondition lhs, final JakCondition rhs ) {
				assertThat( lhs, is( lhsCond ) );
				assertThat( rhs, is( rhsCond ) );
			}
		} );		
	}
	
	@Test
	public final void xoringDirect() {
		xor( lhsCond, rhsCond ).accept( new TestVisitor() {
			@Override
			protected void xor( final JakCondition lhs, final JakCondition rhs ) {
				assertThat( lhs, is( lhsCond ) );
				assertThat( rhs, is( rhsCond ) );
			}
		} );	
	}
	
	@Test
	public final void xoringChain() {
		lhsCond.xor( rhsCond ).accept( new TestVisitor() {
			@Override
			protected void xor( final JakCondition lhs, final JakCondition rhs ) {
				assertThat( lhs, is( lhsCond ) );
				assertThat( rhs, is( rhsCond ) );
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
		protected void not( final JakCondition cond ) {
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
		
		@Override
		protected void xor(
			final JakCondition lhs,
			final JakCondition rhs )
		{
			throw new UnsupportedOperationException();
		}
	}
	
	private static final class FakeJakExpression implements JakExpression {
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
