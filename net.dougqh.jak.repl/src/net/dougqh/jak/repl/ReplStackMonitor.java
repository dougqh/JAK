package net.dougqh.jak.repl;

import java.lang.reflect.Type;
import java.util.List;

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
	public final List< Type > stackTypes() {
		return this.stack.stackTypes();
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
					if ( Types.isCategory1( type ) ) {
						this.repl.codeWriter().
							dup().
							this_getstatic( JakRepl.STATE_FIELD ).
							swap().
							invokevirtual( ReplState.class, pushMethod( type ) );
					} else {
						this.repl.codeWriter().
							dup2().
							this_getstatic( JakRepl.STATE_FIELD ).
							dup_x2().
							pop().
							invokevirtual( ReplState.class, pushMethod( type ) );
					}
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
		this.invoke( popMethod( type ) );
	}

	public final void pop() {
		this.stack.pop();
		this.invoke( method( "pop" ) );
	}

	public final void pop2() {
		this.stack.pop2();
		this.invoke( method( "pop2" ) );
	}

	public final void swap() {
		this.stack.swap();
		this.invoke( method( "swap" ) );
	}

	public final void dup() {
		this.stack.dup();
		this.invoke( method( "dup" ) );
	}

	public final void dup_x1() {
		this.stack.dup_x1();
	}

	public final void dup_x2() {
		this.stack.dup_x2();
	}

	public final void dup2() {
		this.stack.dup2();
	}

	public final void dup2_x1() {
		this.stack.dup2_x1();
	}

	public final void dup2_x2() {
		this.stack.dup2_x2();
	}

	public final int maxStack() {
		return this.stack.maxStack();
	}
	
	private final void invoke( final JavaMethodDescriptor method ) {
		if ( this.isStackTrackingEnabled() ) {
			this.suppressStackTracking();
			try {
				this.repl.suppressRecording();
				try {
					this.repl.codeWriter().
						this_getstatic( JakRepl.STATE_FIELD ).
						invokevirtual( ReplState.class, method );
				} finally {
					this.repl.restoreRecording();
				}
			} finally {
				this.restoreStackTracking();
			}
		}		
	}
	
	private static final JavaMethodDescriptor pushMethod( final Type type ) {
		if ( Types.isIntegerType( type ) ) {
			return JavaAssembler.method( void.class, "push", int.class );
		} else if ( Types.isReferenceType( type ) ) {
			return JavaAssembler.method( void.class, "push", Object.class );
		} else {
			return JavaAssembler.method( void.class, "push", type );
		}
	}
	
	private static final JavaMethodDescriptor popMethod( final Type type ) {
		if ( Types.isCategory2( type ) ) {
			return method( "pop2" );
		} else {
			return method( "pop" );
		}
	}
	
	private static final JavaMethodDescriptor method( final String methodName ) {
		return JavaAssembler.method( void.class, methodName );
	}
}
