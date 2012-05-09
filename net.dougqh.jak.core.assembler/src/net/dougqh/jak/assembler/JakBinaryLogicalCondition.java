package net.dougqh.jak.assembler;

abstract class JakBinaryLogicalCondition extends JakCondition {
	final String operator;
	final JakCondition lhsCondition;
	final JakCondition rhsCondition;
	
	protected JakBinaryLogicalCondition(
		final String operator,
		final JakCondition lhs,
		final JakCondition rhs )
	{
		this.operator = operator;
		this.lhsCondition = lhs;
		this.rhsCondition = rhs;
	}
	
	@Override
	public JakCondition inverse() {
		//DQH - Yes, this code be implemented with D'Morgan's, but
		//I question the useful of implementing it.
		return null;
	}
	
	@Override
	public final int hashCode() {
		int prime = 17;
		
		int hash = this.operator.hashCode();
		hash *= hash;
		hash ^= this.lhsCondition.hashCode();
		hash *= prime;
		hash ^= this.rhsCondition.hashCode();
		hash *= prime;
		
		return prime;
	}
	
	@Override
	public final boolean equals( final Object obj ) {
		if ( obj == this ) {
			return true;
		} else if ( ! ( obj instanceof JakBinaryLogicalCondition ) ) {
			return false;
		} else {
			JakBinaryLogicalCondition that = (JakBinaryLogicalCondition)obj;
			if ( ! this.operator.equals( that.operator ) ) {
				return false;
			} else if ( ! this.lhsCondition.equals( that.lhsCondition ) ) {
				return false;
			} else if ( ! this.rhsCondition.equals( that.rhsCondition ) ) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	@Override
	public final String toString() {
		return "(" + this.lhsCondition + ") " + this.operator + " (" + this.rhsCondition + ")";
	}	
}
