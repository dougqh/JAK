package net.dougqh.jak.assembler;

public abstract class JakCondition {
	/**
	 * and-s this condition as the left hand side with the provided 
	 * right hand side condition.
	 * @param rhsCondition - a {@link JakCondition}
	 * @pre rhsCondition != null
	 * @return a {@link JakCondition}
	 * @post result != null
	 */
	public final JakCondition and( final JakCondition rhsCondition ) {
		return JakAsm.and( this, rhsCondition );
	}
	
	/**
	 * or-s this condition as the left hand side with the provided 
	 * right hand side condition.
	 * @param rhsCondition - a {@link JakCondition}
	 * @pre rhsCondition != null
	 * @return a {@link JakCondition}
	 * @post result != null
	 */
	public final JakCondition or( final JakCondition rhsCondition ) {
		return JakAsm.or( this, rhsCondition );
	}
	
	/**
	 * xor-s this condition as the left hand side with the provided 
	 * right hand side condition.
	 * @param rhsCondition - a {@link JakCondition}
	 * @pre rhsCondition != null
	 * @return a {@link JakCondition}
	 * @post result != null
	 */
	public final JakCondition xor( final JakCondition rhsCondition ) {
		return JakAsm.xor( this, rhsCondition );
	}

	/**
	 * Provides a way of expressing the opposite condition.
	 * Can be used during assembling to rewrite more efficiently.
	 * May return null - if no simple inverse is available.
	 * @return a {@link JakCondition}
	 */
	protected abstract JakCondition optimizedInverse();

	public abstract void accept( final Visitor visitor );
	
	@Override
	public abstract boolean equals( final Object obj );
	
	@Override
	public abstract int hashCode();
	
	@Override
	public abstract String toString();
	
	public static abstract class Visitor {
		protected abstract void lt(
			final JakExpression lhs,
			final JakExpression rhs );

		protected abstract void le(
			final JakExpression lhs,
			final JakExpression rhs );
		
		protected abstract void gt(
			final JakExpression lhs,
			final JakExpression rhs );
		
		protected abstract void ge(
			final JakExpression lhs,
			final JakExpression rhs );
		
		protected abstract void eq(
			final JakExpression lhs,
			final JakExpression rhs );
		
		protected abstract void ne(
			final JakExpression lhs,
			final JakExpression rhs );
		
		protected abstract void truthy(
			final JakExpression expr );
		
		protected abstract void falsy(
			final JakExpression expr );
		
		protected abstract void not(
			final JakCondition cond );
		
		protected abstract void and(
			final JakCondition lhs,
			final JakCondition rhs );
		
		protected abstract void or(
			final JakCondition lhs,
			final JakCondition rhs );
		
		protected abstract void xor(
			final JakCondition lhs,
			final JakCondition rhs );
	}
}