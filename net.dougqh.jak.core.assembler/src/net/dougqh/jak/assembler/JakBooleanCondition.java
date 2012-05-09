package net.dougqh.jak.assembler;

abstract class JakBooleanCondition extends JakCondition {
	final JakExpression expr;
	final boolean expected;
	
	public JakBooleanCondition(
		final JakExpression expr,
		final boolean expected )
	{
		this.expr = expr;
		this.expected = expected;
	}
	
	@Override
	public final int hashCode() {
		if ( this.expected ) {
			return this.expr.hashCode();
		} else {
			return -this.expr.hashCode();
		}
	}
	
	@Override
	public final boolean equals( final Object obj ) {
		if ( obj == this ) {
			return true;
		} else if ( ! ( obj instanceof JakBooleanCondition ) ) {
			return false;
		} else {
			JakBooleanCondition that = (JakBooleanCondition)obj;
			return this.expected == that.expected &&
				this.expr.equals( that.expr );
		}
	}
	
	@Override
	public final String toString() {
		if ( this.expected ) {
			return this.expr.toString();
		} else {
			return "!(" + this.expr.toString() + ")";
		}
	}
}
