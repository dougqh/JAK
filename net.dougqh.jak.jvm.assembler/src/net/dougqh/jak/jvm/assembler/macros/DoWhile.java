package net.dougqh.jak.jvm.assembler.macros;

import net.dougqh.jak.assembler.JakCondition;
import net.dougqh.jak.jvm.assembler.JvmMacro;

public final class DoWhile extends JvmMacro {
	private final stmt body;
	private JakCondition whileCondition = null;
	
	public DoWhile( final stmt body ) {
		this.body = body;
	}
	
	public final DoWhile addWhile( final JakCondition condition ) {
		this.whileCondition = condition;
		return this;
	}
	
	@Override
	protected final void write() {
		if ( this.whileCondition == null ) {
			throw new IllegalStateException( "No while condition specified" );
		}
		
		startLabelScope();
		try {
			startScope();
			try {
				label( "body" );
				startScope();
				macro( this.body );
				endScope();
				
				label( "test" );
				if_( this.whileCondition, "body" );
			} finally {
				endScope();
			}
		} finally {
			endLabelScope();
		}
	}
}
