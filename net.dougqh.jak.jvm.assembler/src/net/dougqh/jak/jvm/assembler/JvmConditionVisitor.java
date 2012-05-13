package net.dougqh.jak.jvm.assembler;

import java.lang.reflect.Type;

import net.dougqh.jak.JakContext;
import net.dougqh.jak.assembler.JakAsm;
import net.dougqh.jak.assembler.JakCondition;
import net.dougqh.jak.assembler.JakExpression;
import net.dougqh.java.meta.types.JavaTypes;

public abstract class JvmConditionVisitor extends JakCondition.Visitor {
	protected final JakContext context;
	
	public JvmConditionVisitor( final JakContext context ) {
		this.context = context;
	}
	
	@Override
	protected final void eq( final JakExpression lhs, final JakExpression rhs ) {
		if ( isInt( lhs ) && isInt( rhs ) ) {
			this.ieq( lhs, rhs );	
		} else if ( isRef( lhs ) && isRef( rhs ) ) {
			this.aeq( lhs, rhs );
		} else if ( isLong( lhs ) && isLong( rhs ) ) {
			this.leq( lhs, rhs );
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	@Override
	protected final void ne( final JakExpression lhs, final JakExpression rhs ) {
		if ( isInt( lhs ) && isInt( rhs ) ) {
			this.ine( lhs, rhs );
		} else if ( isRef( lhs ) && isRef( rhs ) ) {
			this.ane( lhs, rhs );
		} else if ( isLong( lhs ) && isLong( rhs ) ) {
			this.lne( lhs, rhs );
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	@Override
	protected final void le( final JakExpression lhs, final JakExpression rhs ) {
		if ( isInt( lhs ) && isInt( rhs ) ) {
			this.ile( lhs, rhs );
		} else if ( isLong( lhs ) && isLong( rhs ) ) {
			this.lle( lhs, rhs );
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	protected final void lt( final JakExpression lhs, final JakExpression rhs ) {
		if ( isInt( lhs ) && isInt( rhs ) ) {
			this.ilt( lhs, rhs );
		} else if ( isLong( lhs ) && isLong( rhs ) ) {
			this.llt( lhs, rhs );
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	protected final void ge( final JakExpression lhs, final JakExpression rhs ) {
		if ( isInt( lhs ) && isInt( rhs ) ) {
			this.ige( lhs, rhs );
		} else if ( isLong( lhs ) && isLong( rhs ) ) {
			this.lge( lhs, rhs );
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	protected final void gt( final JakExpression lhs, final JakExpression rhs ) {
		if ( isInt( lhs ) && isInt( rhs ) ) {
			this.igt( lhs, rhs );			
		} else if ( isLong( lhs ) && isLong( rhs ) ) {
			this.lgt( lhs, rhs );
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	@Override
	protected final void truthy( final JakExpression expr ) {
		this.ine( expr, JakAsm.const_( 0 ) );
	}
	
	@Override
	protected final void falsy( final JakExpression expr ) {
		this.ieq( expr, JakAsm.const_( 0 ) );
	}
	
	protected abstract void ieq( final JakExpression lhs, final JakExpression rhs );
	
	protected abstract void ine( final JakExpression lhs, final JakExpression rhs );
	
	protected abstract void ilt( final JakExpression lhs, final JakExpression rhs );
	
	protected abstract void ile( final JakExpression lhs, final JakExpression rhs );
	
	protected abstract void igt( final JakExpression lhs, final JakExpression rhs );
	
	protected abstract void ige( final JakExpression lhs, final JakExpression rhs );
	
	
	protected abstract void leq( final JakExpression lhs, final JakExpression rhs );
	
	protected abstract void lne( final JakExpression lhs, final JakExpression rhs );
	
	protected abstract void llt( final JakExpression lhs, final JakExpression rhs );
	
	protected abstract void lle( final JakExpression lhs, final JakExpression rhs );
	
	protected abstract void lgt( final JakExpression lhs, final JakExpression rhs );
	
	protected abstract void lge( final JakExpression lhs, final JakExpression rhs );
	
	
	protected abstract void aeq( final JakExpression lhs, final JakExpression rhs );
	
	protected abstract void ane( final JakExpression lhs, final JakExpression rhs );
	
	protected final boolean isInt( final JakExpression expr ) {
		Type type = expr.type( this.context );
		return type.equals( boolean.class ) ||
			type.equals( byte.class ) ||
			type.equals( char.class ) ||
			type.equals( short.class ) ||
			type.equals( int.class );
	}
	
	protected final boolean isRef( final JakExpression expr ) {
		Type type = expr.type( this.context );
		return JavaTypes.getRawClass( type ).isAssignableFrom( Object.class );
	}
	
	protected final boolean isLong( final JakExpression expr ) {
		return expr.type( this.context ).equals( long.class );
	}
	
	protected final boolean isZero( final JakExpression expr ) {
		if ( expr.hasConstantValue( this.context ) ) {
			Object object = expr.constantValue( this.context );
			
			if ( object instanceof Boolean ) {
				Boolean booleanValue = (Boolean)object;
				return booleanValue.equals( Boolean.FALSE );
			} else if ( object instanceof Number ) {
				Number numberValue = (Number)object;
				return ( numberValue.intValue() == 0 );
			} else {
				return false;
			}
		}
		return false;
	}
	
	protected final boolean isNull( final JakExpression expr ) {
		return expr.hasConstantValue( this.context ) &&
			 ( expr.constantValue( this.context ) == null );
	}
}
