package net.dougqh.jak.assembler;

import java.lang.reflect.Type;

import net.dougqh.jak.Jak;
import net.dougqh.jak.JakContext;

public class JakAsm extends Jak {
	public static final Type thisType_() {
		return ThisType.INSTANCE;
	}
	
	public static final Type superType() {
		return SuperType.INSTANCE;
	}
	
	public static final JakExpression this_() {
		return new JakVariableExpression( "this" ) {
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.this_();
			}
			
			@Override
			public final Type type( final JakContext context ) {
				return context.thisType();
			}
		};		
	}
	
	public static final JakExpression var( final String var ) {
		return new JakVariableExpression( var ) {
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.var( this.name, this.type( visitor.context ) );
			}
			
			@Override
			public final Type type( final JakContext context ) {
				return context.localType( this.name );
			}
		};
	}
	
	public static final JakExpression null_( final Type type ) {
		return new JakConstantExpression(type, null) {
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.null_( type );
			}
		};
	};
	
	public static final JakExpression null_() {
		return null_( Object.class );
	}
	
	public static final JakExpression true_() {
		return const_( true );
	}
	
	public static final JakExpression false_() {
		return const_( false );
	}
	
	public static final JakExpression const_( final boolean value ) {
		return new JakConstantExpression( boolean.class, value ) {
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.const_( value );
			}
		};
	}
	
	public static final JakExpression const_( final byte value ) {
		return new JakConstantExpression( byte.class, value ) {
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.const_( value );
			}
		};
	}
	
	public static final JakExpression const_( final char value ) {
		return new JakConstantExpression( char.class, value ) {
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.const_( value );
			}
		};
	}
	
	public static final JakExpression const_( final short value ) {
		return new JakConstantExpression( short.class, value ) {
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.const_( value );
			}
		};		
	}
	
	public static final JakExpression const_( final int value ) {
		return new JakConstantExpression( int.class, value ) {
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.const_( value );
			}
		};		
	}
	
	public static final JakExpression const_( final long value ) {
		return new JakConstantExpression( long.class, value ) {
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.const_( value );
			}
		};
	}
	
	public static final JakExpression const_( final float value ) {
		return new JakConstantExpression( float.class, value ) {
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.const_( value );
			}
		};
	}
	
	public static final JakExpression const_( final double value ) {
		return new JakConstantExpression( double.class, value ) {
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.const_( value );
			}
		};
	}
	
	public static final JakExpression const_( final String value ) {
		return new JakConstantExpression( String.class, value ) {
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.const_( value );
			}
		};
	}
	
	public static final JakExpression const_( final Type value ) {
		return new JakConstantExpression( Class.class, value ) {
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.const_( value );
			}
		};		
	}
	
	public static final JakCondition not( final String var ) {
		//DQH - somewhat pointless - equivalent to falsy
		return not( truthy( var ) );
	}
	
	public static final JakCondition not( final JakCondition cond ) {
		JakCondition inverse = cond.optimizedInverse();
		if ( inverse != null ) {
			return inverse;
		} else {
			return new UnaryLogicalCondition( "!", cond ) {
				@Override
				public final void accept( final Visitor visitor ) {
					visitor.not( this.condition );
				}
				
				@Override
				protected final JakCondition optimizedInverse() {
					return this.condition;
				}
			};			
		}
	}
	
	public static final JakCondition and(
		final JakCondition lhs,
		final JakCondition rhs )
	{
		return new BinaryLogicalCondition( "&&", lhs, rhs ) {
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.and( this.lhsCondition, this.rhsCondition );
			}
			
			@Override
			protected final JakCondition optimizedInverse() {
				//DQH - Could implement D'Morgan's, but I expect it has limited value.
				return null;
			}
		};		
	}
	
	public static final JakCondition and(
		final JakExpression lhsExpr,
		final JakExpression rhsExpr )
	{
		return and( truthy( lhsExpr ), truthy( rhsExpr ) );
	}
	
	public static final JakCondition and(
		final String lhsVar,
		final String rhsVar )
	{
		return and( var( lhsVar ), var( rhsVar ) );
	}
	
	public static final JakCondition or(
		final JakCondition lhs,
		final JakCondition rhs )
	{
		return new BinaryLogicalCondition( "||", lhs, rhs ) {
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.or( this.lhsCondition, this.rhsCondition );
			}
			
			@Override
			protected final JakCondition optimizedInverse() {
				//DQH - Could implement D'Morgan's, but I expect it has limited value.
				return null;
			}
		};
	}
	
	public static final JakCondition or(
		final JakExpression lhsExpr,
		final JakExpression rhsExpr )
	{
		return or( truthy( lhsExpr ), truthy( rhsExpr ) );
	}
	
	public static final JakCondition or(
		final String lhsVar,
		final String rhsVar )
	{
		return or( var( lhsVar ), var( rhsVar ) );
	}
	
	public static final JakCondition xor(
		final JakCondition lhs,
		final JakCondition rhs )
	{
		return new BinaryLogicalCondition( "^", lhs, rhs ) {
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.xor( this.lhsCondition, this.rhsCondition );
			}
			
			@Override
			protected final JakCondition optimizedInverse() {
				return null;
			}
		};
	}
	
	public static final JakCondition xor(
		final JakExpression lhsExpr,
		final JakExpression rhsExpr )
	{
		return xor( truthy( lhsExpr ), truthy( rhsExpr ) );
	}
	
	public static final JakCondition xor(
		final String lhsVar,
		final String rhsVar )
	{
		return xor( var( lhsVar ), var( rhsVar ) );
	}
	
	public static final JakCondition truthy( final String var ) {
		return truthy( var( var ) );
	}
	
	public static final JakCondition truthy( final JakExpression expr ) {
		return new BooleanCondition( expr, true ) {
			@Override
			protected final JakCondition optimizedInverse() {
				return falsy( this.expr );
			}
			
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.truthy( this.expr );
			}
		};		
	}
	
	public static final JakCondition falsy( final String var ) {
		return falsy( var( var ) );
	}
	
	public static final JakCondition falsy( final JakExpression expr ) {
		return new BooleanCondition( expr, false ) {
			@Override
			protected final JakCondition optimizedInverse() {
				return truthy( this.expr );
			}
			
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.falsy( this.expr );
			}
		};
	}
	
	public static final JakCondition eq(
		final String var,
		final int value )
	{
		return eq( var( var ), const_( value ) );
	}
	
	public static final JakCondition eq(
		final String lhsVar,
		final JakExpression rhsExpr )
	{
		return eq( var( lhsVar ), rhsExpr );
	}
	
	public static final JakCondition eq(
		final String lhs,
		final String rhs )
	{
		return eq( var( lhs ), var( rhs ) );
	}
	
	public static final JakCondition eq(
		final JakExpression lhs,
		final JakExpression rhs )
	{
		return new BinaryComparisonCondition( "==", lhs, rhs ) {
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.eq( this.lhsExpression, this.rhsExpression );
			}
			
			@Override
			protected final JakCondition optimizedInverse() {
				return ne( this.lhsExpression, this.rhsExpression );
			}
		};
	}
	
	public static final JakCondition ne(
		final String var,
		final int value )
	{
		return ne( var( var ), const_( value ) );
	}
	
	public static final JakCondition ne(
		final String lhs,
		final String rhs )
	{
		return ne( var( lhs ), var( rhs ) );
	}
	
	public static final JakCondition ne(
		final String lhsVar,
		final JakExpression rhsExpr )
	{
		return ne( var( lhsVar ), rhsExpr );
	}
	
	public static final JakCondition ne(
		final JakExpression lhs,
		final JakExpression rhs )
	{
		return new BinaryComparisonCondition( "!=", lhs, rhs ) {
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.ne( this.lhsExpression, this.rhsExpression );
			}
			
			@Override
			protected final JakCondition optimizedInverse() {
				return eq( this.lhsExpression, this.rhsExpression );
			}
		};		
	}
	
	public static final JakCondition lt(
		final String var,
		final int value )
	{
		return lt( var( var ), const_( value ) );
	}
	
	public static final JakCondition lt(
		final String lhs,
		final String rhs )
	{
		return lt( var( lhs ), var( rhs ) );
	}
	
	public static final JakCondition lt(
		final String lhsVar,
		final JakExpression rhsExpr )
	{
		return lt( var( lhsVar ), rhsExpr );
	}
	
	public static final JakCondition lt(
		final JakExpression lhs,
		final JakExpression rhs )
	{
		return new BinaryComparisonCondition( "<", lhs, rhs ) {
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.lt( this.lhsExpression, this.rhsExpression );
			}
			
			@Override
			protected final JakCondition optimizedInverse() {
				return ge( this.lhsExpression, this.rhsExpression );
			}
		};
	}
	
	public static final JakCondition le(
		final String var,
		final int value )
	{
		return le( var( var ), const_( value ) );
	}
	
	public static final JakCondition le(
		final String lhsVar,
		final JakExpression rhsExpr )
	{
		return le( var( lhsVar ), rhsExpr );
	}
	
	public static final JakCondition le(
		final String lhs,
		final String rhs )
	{
		return le( var( lhs ), var( rhs ) );
	}
	
	public static final JakCondition le(
		final JakExpression lhs,
		final JakExpression rhs )
	{
		return new BinaryComparisonCondition( "<=", lhs, rhs ) {
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.le( this.lhsExpression, this.rhsExpression );
			}
			
			@Override
			protected final JakCondition optimizedInverse() {
				return gt( this.lhsExpression, this.rhsExpression );
			}
		};
	}
	
	public static final JakCondition gt(
		final String var,
		final int value )
	{
		return gt( var( var ), const_( value ) );
	}
	
	public static final JakCondition gt(
		final String lhs,
		final String rhs )
	{
		return gt( var( lhs ), var( rhs ) );
	}
	
	public static final JakCondition gt(
		final String lhsVar,
		final JakExpression rhsExpr )
	{
		return gt( var( lhsVar ), rhsExpr );
	}
	
	public static final JakCondition gt(
		final JakExpression lhs,
		final JakExpression rhs )
	{
		return new BinaryComparisonCondition( ">", lhs, rhs ) {
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.gt( this.lhsExpression, this.rhsExpression );
			}
			
			@Override
			protected final JakCondition optimizedInverse() {
				return le( this.lhsExpression, this.rhsExpression );
			}
		};
	}
	
	public static final JakCondition ge(
		final String var,
		final int value )
	{
		return ge( var( var ), const_( value ) );
	}
	
	public static final JakCondition ge(
		final String lhs,
		final String rhs )
	{
		return ge( var( lhs ), var( rhs ) );
	}

	public static final JakCondition ge(
		final String lhsVar,
		final JakExpression rhsExpr )
	{
		return ge( var( lhsVar ), rhsExpr );
	}
	
	public static final JakCondition ge(
		final JakExpression lhs,
		final JakExpression rhs )
	{
		return new BinaryComparisonCondition( ">=", lhs, rhs ) {
			@Override
			public final void accept( final Visitor visitor ) {
				visitor.ge( lhs, rhs );
			}
			
			@Override
			protected final JakCondition optimizedInverse() {
				return lt( this.lhsExpression, this.rhsExpression );
			}
		};
	}
}
