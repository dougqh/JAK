package net.dougqh.jak.repl;

import java.io.IOException;

enum StatePart implements ReplEnum< StatePart > {
	STACK( "stack" ) {
		@Override
		boolean get( final ReplConfig config ) {
			return config.autoShowStack();
		}
		
		@Override
		final void set( final ReplConfig config, final boolean value ) {
			config.autoShowStack( value );
		}
		
		@Override
		final void print( final ReplState state, final ReplConsole console )
			throws IOException
		{
			state.printStack( console );
		}
	},
	LOCALS( "locals" ) {
		@Override
		boolean get( final ReplConfig config ) {
			return config.autoShowLocals();
		}
		
		@Override
		final void set( final ReplConfig config, final boolean value ) {
			config.autoShowLocals( value );
		}
		
		@Override
		final void print( final ReplState state, final ReplConsole console )
			throws IOException
		{
			state.printLocals( console );
		}
	};

	public static final StatePart parse( final String stringValue ) {
		return ReplFormatter.parse( StatePart.class, stringValue );
	}
	
	private final String id;
	
	StatePart( final String id ) {
		this.id = id;
	}
	
	@Override
	public final String id() {
		return this.id;
	}
	
	abstract boolean get( final ReplConfig config );
	
	abstract void set( final ReplConfig config, final boolean value );
	
	abstract void print( final ReplState state, final ReplConsole console )
		throws IOException;
	
	@Override
	public final String toString() {
		return this.id;
	}
}
