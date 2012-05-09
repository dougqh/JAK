package net.dougqh.jak.assembler;

import java.lang.reflect.Type;

import net.dougqh.jak.JakContext;

abstract class JakConstantExpression extends JakExpression {
	private final Type type;
	private final Object value;
	
	JakConstantExpression( final Type type, final Object value ) {
		this.type = type;
		this.value = value;
	}
	
	@Override
	public final Type type( final JakContext context ) {
		return this.type;
	}
	
	@Override
	public final boolean hasConstantValue( final JakContext context ) {
		return true;
	}
	
	@Override
	public final Object constantValue( final JakContext context ) {
		return this.value;
	}

	@Override
	public final int hashCode() {
		if ( this.value == null ) {
			return 0;
		} else {
			return this.value.hashCode();
		}
	}
	
	@Override
	public final boolean equals( final Object obj ) {
		if ( obj == this ) {
			return true;
		} else if ( ! ( obj instanceof JakConstantExpression ) ) {
			return false;
		} else {
			JakConstantExpression that = (JakConstantExpression)obj;
			if ( ! this.type.equals( that.type ) ) {
				return false;
			}
			if ( this.value == null ) {
				return ( that.value == null );
			} else {
				return this.value.equals( that.value );
			}
		}
	}
	
	@Override
	public final String toString() {
		return this.value.toString();
	}
}
