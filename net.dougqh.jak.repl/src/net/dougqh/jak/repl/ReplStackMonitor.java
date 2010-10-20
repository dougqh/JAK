package net.dougqh.jak.repl;

import java.lang.reflect.Type;

import net.dougqh.jak.JakTypeStack;
import net.dougqh.jak.JavaAssembler;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.StackMonitor;
import net.dougqh.jak.types.Types;

final class ReplStackMonitor implements StackMonitor {
	private final JakRepl repl;
	private final StackMonitor stack;
	
	private int suppressionCount = 0;
	
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
	
	final void suppressStackTracking() {
		++this.suppressionCount;
	}
	
	final void restoreStackTracking() {
		--this.suppressionCount;
	}
	
	final boolean isStackTrackingEnabled() {
		return ( this.suppressionCount == 0 );
	}

	public final void stack( final Type type ) {
		this.stack.stack( type );
		
		if ( this.isStackTrackingEnabled() ) {
			this.suppressStackTracking();
			try {
				this.repl.suppressRecording();
				try {
					repl.stateCodeWriter().push( type );
				} finally {
					this.repl.restoreRecording();
				}
			} finally {
				this.restoreStackTracking();
			}
		}
	}

	public final void unstack( final Type type ) {
		this.stack.unstack( type );

		if ( this.isStackTrackingEnabled() ) {
			this.suppressStackTracking();
			try {
				this.repl.suppressRecording();
				try {
					this.repl.stateCodeWriter().unstack( type );
				} finally {
					this.repl.restoreRecording();
				}
			} finally {
				this.restoreStackTracking();
			}
		}
	}

	public final void pop() {
		this.stack.pop();
		this.invoke( "pop" );
	}

	public final void pop2() {
		this.stack.pop2();
		this.invoke( "pop2" );
	}

	public final void swap() {
		this.stack.swap();
		this.invoke( "swap" );
	}

	public final void dup() {
		this.stack.dup();
		this.invoke( "dup" );
	}

	public final void dup_x1() {
		this.stack.dup_x1();
		this.invoke( "dup_x1" );
	}

	public final void dup_x2() {
		this.stack.dup_x2();
		this.invoke( "dup_x2" );
	}

	public final void dup2() {
		this.stack.dup2();
		this.invoke( "dup2" );
	}

	public final void dup2_x1() {
		this.stack.dup2_x1();
		this.invoke( "dup2_x1" );
	}

	public final void dup2_x2() {
		this.stack.dup2_x2();
		this.invoke( "dup2_x2" );
	}

	public final int maxStack() {
		return this.stack.maxStack();
	}
	
	private final void invoke( final String method ) {
		if ( this.isStackTrackingEnabled() ) {
			this.suppressStackTracking();
			try {
				this.repl.suppressRecording();
				try {
					this.repl.stateCodeWriter().invoke( method );
				} finally {
					this.repl.restoreRecording();
				}
			} finally {
				this.restoreStackTracking();
			}
		}
	}
	
	private static final JavaMethodDescriptor method( final String methodName ) {
		return JavaAssembler.method( void.class, methodName );
	}
}
