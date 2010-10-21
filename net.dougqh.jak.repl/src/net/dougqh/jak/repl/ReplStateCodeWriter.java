package net.dougqh.jak.repl;

import static net.dougqh.jak.JavaAssembler.*;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaAssembler;
import net.dougqh.jak.JavaFieldDescriptor;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.types.Types;

final class ReplStateCodeWriter {
	static final JavaFieldDescriptor STATE_FIELD = 
		public_().static_().field( ReplState.class, "state" );

	private final JakRepl repl;
	private int suppressionCount = 0;
	
	ReplStateCodeWriter( final JakRepl repl ) {
		this.repl = repl;
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
	
	final void push( final Type type ) {
		if ( this.isStackTrackingEnabled() ) {
			this.suppressStackTracking();
			try {
				this.repl.suppressRecording();
				try {
					if ( Types.isCategory1( type ) ) {
						this.repl.codeWriter().
							dup().
							self_getstatic( STATE_FIELD ).
							swap().
							invokevirtual( ReplState.class, pushMethod( type ) );
					} else {
						this.repl.codeWriter().
							dup2().
							self_getstatic( STATE_FIELD ).
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
	
	final void pushUninitialized() {
		this.invoke( pushUninitializedMethod() );
	}
	
	final void unstack( final Type type ) {
		this.invoke( popMethod( type ) );
	}
	
	final void invoke( final String name ) {
		this.invoke( method( name ) );
	}
	
	private final void invoke( final JavaMethodDescriptor method ) {
		if ( this.isStackTrackingEnabled() ) {
			this.suppressStackTracking();
			try {
				this.repl.suppressRecording();
				try {
					this.repl.codeWriter().
						self_getstatic( STATE_FIELD ).
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
	
	private static final JavaMethodDescriptor pushUninitializedMethod() {
		return JavaAssembler.method( Object.class, "pushUninitialized" );
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
