package net.dougqh.jak.jvm.assembler.macros;

import net.dougqh.jak.jvm.assembler.JvmMacro;

public final class Synchronized extends JvmMacro {
	protected static final String var_synchronized = "synched";
	
	private final String synchronizedVar;
	private final stmt body;
	
	public Synchronized( final String var, final stmt body ) {
		this.synchronizedVar = var;
		this.body = body;
	}

	@Override
	public final void write() {
		startLabelScope();
		try {
			startScope();
			try {
				alias( var_synchronized, this.synchronizedVar );
				monitorenter( var_synchronized );
				label( "try" );
				macro( this.body );
				label( "endTry" );
				catch_( "try", "endTry", Throwable.class );
				adeclare( "e" );
				astore( "e" );
				monitorexit( var_synchronized ).
				aload("e").
				athrow();
				
			} finally {
				endScope();
			}
		} finally {
			endLabelScope();
		}
	}
}
