package net.dougqh.jak.assembler;

abstract class UnaryLogicalCondition extends JakCondition {
	final String operator;
	final JakCondition condition;
	
	protected UnaryLogicalCondition(
		final String operator,
		final JakCondition condition )
	{
		this.operator = operator;
		this.condition = condition;
	}
	
	@Override
	public final int hashCode() {
		int prime = 17;
		
		int hash = this.operator.hashCode();
		hash *= hash;
		hash ^= this.condition.hashCode();
		hash *= prime;
		
		return prime;
	}
	
	@Override
	public final boolean equals( final Object obj ) {
		if ( obj == this ) {
			return true;
		} else if ( ! ( obj instanceof UnaryLogicalCondition ) ) {
			return false;
		} else {
			UnaryLogicalCondition that = (UnaryLogicalCondition)obj;
			if ( ! this.operator.equals( that.operator ) ) {
				return false;
			} else if ( ! this.condition.equals( that.condition ) ) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	@Override
	public final String toString() {
		return this.operator + "( " + this.condition + ") ";
	}	
}
