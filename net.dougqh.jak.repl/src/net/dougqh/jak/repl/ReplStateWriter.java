package net.dougqh.jak.repl;

import static net.dougqh.jak.JavaAssembler.*;

import java.lang.reflect.Type;

import net.dougqh.jak.JavaAssembler;
import net.dougqh.jak.JavaCodeWriter;
import net.dougqh.jak.JavaFieldDescriptor;
import net.dougqh.jak.JavaMethodDescriptor;
import net.dougqh.jak.types.Types;

final class ReplStateCodeWriter {
	static final JavaFieldDescriptor STATE_FIELD = 
		public_().static_().field( ReplState.class, "state" );

	private final JavaCodeWriter codeWriter;
	
	ReplStateCodeWriter( final JavaCodeWriter codeWriter ) {
		this.codeWriter = codeWriter;
	}
	
	final void push( final Type type ) {
		if ( Types.isCategory1( type ) ) {
			this.codeWriter.
				dup().
				self_getstatic( STATE_FIELD ).
				swap().
				invokevirtual( ReplState.class, pushMethod( type ) );
		} else {
			this.codeWriter.
				dup2().
				self_getstatic( STATE_FIELD ).
				dup_x2().
				pop().
				invokevirtual( ReplState.class, pushMethod( type ) );
			
		}
	}
	
	final void unstack( final Type type ) {
		this.invoke( popMethod( type ) );
	}
	
	final void invoke( final String name ) {
		this.invoke( method( name ) );
	}
	
	private final void invoke( final JavaMethodDescriptor method ) {
		this.codeWriter.
			self_getstatic( STATE_FIELD ).
			invokevirtual( ReplState.class, method );
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
