package net.dougqh.jak.jvm.assembler.macros;

import java.lang.reflect.Type;

import net.dougqh.jak.JakContext;
import net.dougqh.jak.assembler.JakCondition;
import net.dougqh.jak.assembler.JakExpression;
import net.dougqh.jak.jvm.assembler.JvmExpression;
import net.dougqh.jak.types.Any;

public final class Ternary extends JvmExpression< Any > {
	private final JakCondition cond;
	private final JakExpression trueExpr;
	private final JakExpression falseExpr;
	
	public Ternary(
		final JakCondition cond,
		final JakExpression trueExpr,
		final JakExpression falseExpr )
	{
		this.cond = cond;
		this.trueExpr = trueExpr;
		this.falseExpr = falseExpr;
	}
	
	@Override
	public final Type type( final JakContext context ) {
		return this.trueExpr.type( context );
	}
	
	@Override
	protected final void eval() {
		startLabelScope();
		try {
			if_( cond, "ternaryTrue" );
			//false
			expr( this.falseExpr );
			goto_( "endTernary" );
			//true
			label( "ternaryTrue" );
			expr( this.trueExpr );
			
			label( "endTernary" );
		} finally {
			endLabelScope();
		}
	}
}
