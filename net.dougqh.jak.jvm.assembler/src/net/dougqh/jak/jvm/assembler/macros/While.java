package net.dougqh.jak.jvm.assembler.macros;

import net.dougqh.jak.assembler.JakCondition;
import net.dougqh.jak.jvm.assembler.JvmMacro;

public final class While extends JvmMacro {
	private final JakCondition condition;
	private final stmt body;
	
	public While( final JakCondition condition, final stmt body ) {
		this.condition = condition;
		this.body = body;
	}
	
	@Override
	protected final void write() {
		startLabelScope();
		try {
			startScope();
			try {
				goto_( "test" );
				
				label( "body" );
				startScope();
				macro( this.body );
				endScope();
				
				label( "test" );
				if_( condition, "body" );
			} finally {
				endScope();
			}
		} finally {
			endLabelScope();
		}
	}
}
