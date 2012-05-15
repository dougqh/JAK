package net.dougqh.jak.assembler;

abstract class BinaryComparisonCondition extends JakCondition {
	final String operator;
	final JakExpression lhsExpression;
	final JakExpression rhsExpression;
	
	public BinaryComparisonCondition(
		final String operator,
		final JakExpression lhsExpression,
		final JakExpression rhsExpression )
	{
		this.operator = operator;
		this.lhsExpression = lhsExpression;
		this.rhsExpression = rhsExpression;
	}
	
	@Override
	public final int hashCode() {
		int prime = 17;
		
		int hash = this.operator.hashCode();
		hash *= hash;
		hash ^= this.lhsExpression.hashCode();
		hash *= prime;
		hash ^= this.rhsExpression.hashCode();
		hash *= prime;
		
		return prime;
	}
	
	@Override
	public final boolean equals( final Object obj ) {
		if ( obj == this ) {
			return true;
		} else if ( ! ( obj instanceof BinaryComparisonCondition ) ) {
			return false;
		} else {
			BinaryComparisonCondition that = (BinaryComparisonCondition)obj;
			if ( ! this.operator.equals( that.operator ) ) {
				return false;
			} else if ( ! this.lhsExpression.equals( that.lhsExpression ) ) {
				return false;
			} else if ( ! this.rhsExpression.equals( that.rhsExpression ) ) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	@Override
	public final String toString() {
		return this.lhsExpression + " " + this.operator + " " + this.rhsExpression;
	}
}
