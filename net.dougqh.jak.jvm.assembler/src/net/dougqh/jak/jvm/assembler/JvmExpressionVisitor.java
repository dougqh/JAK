package net.dougqh.jak.jvm.assembler;

import net.dougqh.jak.JakContext;

public abstract class JvmExpressionVisitor extends JvmExpression.Visitor {
	public JvmExpressionVisitor( final JakContext context ) {
		super( context );
	}
	
	@Override
	protected void const_( final boolean value ) {
		this.const_( value ? 1 : 0 );
	}
	
	@Override
	protected void const_( final byte value ) {
		this.const_( value );
	}
	
	@Override
	protected void const_( final char value ) {
		this.const_( value );
	}
	
	@Override
	protected void const_( final short value ) {
		this.const_( value );
	}
	
	@Override
	protected void const_( final int value ) {
		this.const_( value );
	}
}
