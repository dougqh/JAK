package net.dougqh.jak.repl;

import static net.dougqh.jak.JavaAssembler.*;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaAssembler;
import net.dougqh.jak.JavaFieldDescriptor;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.types.Types;
import net.dougqh.java.meta.types.JavaTypes;

final class ReplStateCodeWriter {
	static final JavaFieldDescriptor STATE_FIELD = 
		public_().static_().field( ReplState.class, "state" );

	private final JakRepl repl;
	private int suppressionCount = 0;
	
	ReplStateCodeWriter( final JakRepl repl ) {
		this.repl = repl;
	}
	
	final void suppressStateTracking() {
		++this.suppressionCount;
	}
	
	final void restoreStateTracking() {
		--this.suppressionCount;
	}
	
	final boolean isStateTrackingEnabled() {
		return ( this.suppressionCount == 0 );
	}
	
	final void push( final Type type ) {
		if ( this.isStateTrackingEnabled() ) {
			this.suppressStateTracking();
			try {
				this.repl.suppressRecording();
				try {
					if ( Types.isReferenceType( type ) ) {
						this.repl.codeWriter().
							dup().
							self_getstatic( STATE_FIELD ).
							swap().
							ldc( JavaTypes.getRawClass( type ) ).
							swap().
							invokevirtual( ReplState.class, pushMethod( type ) );
					} else if ( Types.isCategory1( type ) ) {
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
				this.restoreStateTracking();
			}
		}				
	}
	
	final void pushUninitialized( final Type type ) {
		if ( this.isStateTrackingEnabled() ) {
			this.suppressStateTracking();
			try {
				this.repl.suppressRecording();
				try {
					this.repl.codeWriter().
						self_getstatic( STATE_FIELD ).						
						ldc( JavaTypes.getRawClass( type ) ).
						invokevirtual( ReplState.class, pushUninitializedMethod() );
				} finally {
					this.repl.restoreRecording();
				}
			} finally {
				this.restoreStateTracking();
			}
		}
	}
	
	final void unstack( final Type type ) {
		this.invoke( popMethod( type ) );
	}
	
	final void storeLocal( final int slot, final Type type ) {
		if ( this.isStateTrackingEnabled() ) {
			this.suppressStateTracking();
			try {
				this.repl.suppressRecording();
				try {
					if ( Types.isReferenceType( type ) ) {
						this.repl.codeWriter().
							dup().
							self_getstatic( STATE_FIELD ).
							iconst( slot ).
							dup2_x1().
							pop2().
							ldc( JavaTypes.getRawClass( type ) ).
							swap().
							invokevirtual( ReplState.class, storeMethod( type ) );
					} else if ( Types.isCategory1( type ) ) {
						this.repl.codeWriter().
							dup().
							self_getstatic( STATE_FIELD ).
							iconst( slot ).
							dup2_x1().
							pop2().
							invokevirtual( ReplState.class, storeMethod( type ) );
					} else {
						this.repl.codeWriter().
							dup2().
							self_getstatic( STATE_FIELD ).
							iconst( slot ).
							dup2_x2().
							pop2().
							invokevirtual( ReplState.class, storeMethod( type ) );
					}					
				} finally {
					this.repl.restoreRecording();
				}
			} finally {
				this.restoreStateTracking();
			}
		}
	}
	
	final void invoke( final String name ) {
		this.invoke( method( name ) );
	}
	
	private final void invoke( final JavaMethodDescriptor method ) {
		if ( this.isStateTrackingEnabled() ) {
			this.suppressStateTracking();
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
				this.restoreStateTracking();
			}
		}
	}
	
	private static final JavaMethodDescriptor pushMethod( final Type type ) {
		if ( Types.isIntegerType( type ) ) {
			return JavaAssembler.method( void.class, "push", int.class );
		} else if ( Types.isReferenceType( type ) ) {
			return JavaAssembler.method( void.class, "push", Type.class, Object.class );
		} else {
			return JavaAssembler.method( void.class, "push", type );
		}
	}
	
	private static final JavaMethodDescriptor storeMethod( final Type type ) {
		if ( Types.isIntegerType( type ) ) {
			return JavaAssembler.method( void.class, "storeLocal", int.class, int.class );
		} else if ( Types.isReferenceType( type ) ) {
			return JavaAssembler.method( void.class, "storeLocal", int.class, Type.class, Object.class );
		} else {
			return JavaAssembler.method( void.class, "storeLocal", int.class, type );
		}
	}
	
	private static final JavaMethodDescriptor pushUninitializedMethod() {
		return JavaAssembler.method( Object.class, "pushUninitialized", Type.class );
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
