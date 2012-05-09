package net.dougqh.jak.jvm.assembler;

import net.dougqh.jak.JakContext;
import net.dougqh.jak.assembler.JakAsm;
import net.dougqh.jak.assembler.JakCondition;
import net.dougqh.jak.assembler.JakExpression;

public abstract class JvmConditionVisitor extends JakCondition.Visitor {
	protected final JakContext context;
	
	public JvmConditionVisitor( final JakContext context ) {
		this.context = context;
	}
	
	@Override
	protected final void eq( final JakExpression lhs, final JakExpression rhs ) {
		this.ieq( lhs, rhs );
	}
	
	@Override
	protected final void ne( final JakExpression lhs, final JakExpression rhs ) {
		this.ine( lhs, rhs );
	}
	
	@Override
	protected final void le( final JakExpression lhs, final JakExpression rhs ) {
		this.ile( lhs, rhs );
	}

	@Override
	protected final void lt( final JakExpression lhs, final JakExpression rhs ) {
		this.ilt( lhs, rhs );
	}

	@Override
	protected final void ge( final JakExpression lhs, final JakExpression rhs ) {
		this.ige( lhs, rhs );
	}

	@Override
	protected final void gt( final JakExpression lhs, final JakExpression rhs ) {
		this.igt( lhs, rhs );
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
