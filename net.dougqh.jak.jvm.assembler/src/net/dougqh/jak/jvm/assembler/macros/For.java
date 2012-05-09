package net.dougqh.jak.jvm.assembler.macros;

public abstract class For extends stmt {
	protected static final String label_test = "test";
	protected static final String label_body = "body";
	protected static final String label_step = "step";
	
	protected static final String label_continue = "continue";
	protected static final String label_break = "break";
	
	protected void preinit() {}
	
	protected abstract void init();
	
	protected void bodyStart() {}
	
	protected abstract void body();
	
	protected abstract void test( final String trueLabel );
	
	protected abstract void step();
	
	@Override
	protected final void write() {
		startLabelScope();
		try {		
			startScope();
			try {
				preinit();
				startScope();
				try {
					init();
					goto_( label_test );
					
					label( label_body );
					startScope();
					try {
						bodyStart();
						body();
					} finally {
						endScope();
					}
					
					label( label_continue );
					label( label_step );
					step();
					
					label( label_test );
					test( label_body );
				} finally {
					endScope();
				}
			} finally {
				endScope();
			}
			label( label_break );
		} finally {
			endLabelScope();
		}
	}
}
