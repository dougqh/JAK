package net.dougqh.jak.repl;

import java.lang.reflect.Type;

import net.dougqh.jak.assembler.JakTypeStack;
import net.dougqh.jak.assembler.StackMonitor;

final class ReplStackMonitor implements StackMonitor {
	private final JakRepl repl;
	private final StackMonitor stack;
	
	ReplStackMonitor(
		final JakRepl repl,
		final StackMonitor stack )
	{
		this.repl = repl;
		this.stack = stack;
	}
	
	@Override
	public final void enableTypeTracking() {
		this.stack.enableTypeTracking();
	}
	
	@Override
	public final JakTypeStack typeStack() {
		return this.stack.typeStack();
	}

	public final void stack( final Type type ) {
		this.stack.stack( type );
		this.repl.stateCodeWriter().push( type );
	}

	public final void unstack( final Type type ) {
		this.stack.unstack( type );

		this.repl.stateCodeWriter().unstack( type );
	}

	public final void pop() {
		this.stack.pop();
		this.repl.stateCodeWriter().invoke( "pop" );
	}

	public final void pop2() {
		this.stack.pop2();
		this.repl.stateCodeWriter().invoke( "pop2" );
	}

	public final void swap() {
		this.stack.swap();
		this.repl.stateCodeWriter().invoke( "swap" );
	}

	public final void dup() {
		this.stack.dup();
		this.repl.stateCodeWriter().invoke( "dup" );
	}

	public final void dup_x1() {
		this.stack.dup_x1();
		this.repl.stateCodeWriter().invoke( "dup_x1" );
	}

	public final void dup_x2() {
		this.stack.dup_x2();
		this.repl.stateCodeWriter().invoke( "dup_x2" );
	}

	public final void dup2() {
		this.stack.dup2();
		this.repl.stateCodeWriter().invoke( "dup2" );
	}

	public final void dup2_x1() {
		this.stack.dup2_x1();
		this.repl.stateCodeWriter().invoke( "dup2_x1" );
	}

	public final void dup2_x2() {
		this.stack.dup2_x2();
		this.repl.stateCodeWriter().invoke( "dup2_x2" );
	}
	
	@Override
	public final Type topType( final Type expectedType ) {
		return this.stack.topType( expectedType );
	}

	public final int maxStack() {
		return this.stack.maxStack();
	}
}
