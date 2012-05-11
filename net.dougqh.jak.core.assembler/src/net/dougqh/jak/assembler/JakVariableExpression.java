package net.dougqh.jak.assembler;

import net.dougqh.jak.JakContext;


abstract class JakVariableExpression implements JakExpression {
	protected final String name;
	
	JakVariableExpression( final String name ) {
		this.name = name;
	}
	
	@Override
	public boolean hasConstantValue( final JakContext context ) {
		return false;
	}
	
	@Override
	public Object constantValue( final JakContext context ) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public final boolean equals( final Object obj ) {
		if ( obj == this ) {
			return true;
		} else if ( ! ( obj instanceof JakVariableExpression ) ) {
			return false;
		} else {
			JakVariableExpression that = (JakVariableExpression)obj;
			return this.name.equals( that.name );
		}
	}
	
	@Override
	public final int hashCode() {
		return this.name.hashCode();
	}
	
	@Override
	public final String toString() {
		return this.name;
	}
}
