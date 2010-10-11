package net.dougqh.jak.repl;

import java.lang.reflect.Type;

import net.dougqh.jak.types.Types;

enum ReplArgument {
	INT( new IntegerTypeMatcher() ) {
		
	},
	FLOAT( float.class ) {
		
	},
	LONG( long.class ) {
		
	},
	DOUBLE( double.class ) {
		
	},
	REFERENCE( new ReferenceTypeMatcher() ) {
		
	};
	
	private final TypeMatcher typeMatcher;
	
	ReplArgument( final Type type ) {
		this( new SingleTypeMatcher( type ) );
	}
	
	ReplArgument( final TypeMatcher typeMatcher ) {
		this.typeMatcher = typeMatcher;
	}
	
	public abstract Object 
	
	private interface TypeMatcher {
		public abstract boolean matches( final Type type );
	}
	
	private static final class IntegerTypeMatcher implements TypeMatcher {
		@Override
		public final boolean matches( final Type type ) {
			return Types.isIntegerType( type );
		}
	}
	
	private static final class ReferenceTypeMatcher implements TypeMatcher {
		@Override
		public final boolean matches( final Type type ) {
			return Types.isReferenceType( type );
		}
	}
	
	private static final class SingleTypeMatcher implements TypeMatcher {
		private final Type type;
		
		SingleTypeMatcher( final Type type ) {
			this.type = type;
		}
		
		@Override
		public final boolean matches( final Type type ) {
			return this.type.equals( type );
		}
	}
}
